package tantrum.fetcher.demo.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.ec2.Ec2Client;
import software.amazon.awssdk.services.ec2.model.DescribeVpcsRequest;
import software.amazon.awssdk.services.ec2.model.DescribeVpcsResponse;
import software.amazon.awssdk.services.lambda.LambdaClient;
import software.amazon.awssdk.services.lambda.model.FunctionConfiguration;
import software.amazon.awssdk.services.lambda.model.GetFunctionRequest;
import software.amazon.awssdk.services.lambda.model.GetFunctionResponse;
import software.amazon.awssdk.services.resourcegroupstaggingapi.ResourceGroupsTaggingApiClient;
import software.amazon.awssdk.services.resourcegroupstaggingapi.model.GetResourcesRequest;
import software.amazon.awssdk.services.resourcegroupstaggingapi.model.GetResourcesResponse;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetBucketLocationRequest;
import software.amazon.awssdk.services.s3.model.GetBucketLocationResponse;
import software.amazon.awssdk.services.s3.model.ListObjectsV2Request;
import software.amazon.awssdk.services.s3.model.ListObjectsV2Response;
import software.amazon.awssdk.services.s3.model.S3Object;
import tantrum.fetcher.demo.models.Ec2Vpc;
import tantrum.fetcher.demo.models.Lambda;
import tantrum.fetcher.demo.models.Resource;
import tantrum.fetcher.demo.models.ResourceTypes;
import tantrum.fetcher.demo.models.S3Bucket;

@Service()
public class ResourceService {

    private final ResourceGroupsTaggingApiClient taggingClient;
    private final S3Client s3Client;
    private final Ec2Client ec2Client;
    private final LambdaClient lambdaClient;

    public ResourceService(
            @Value("${aws.accessKeyId}") String accessKeyId,
            @Value("${aws.secretKey}") String secretKey,
            @Value("${aws.region}") String regionKey) {
        AwsBasicCredentials awsCredentials = AwsBasicCredentials.create(accessKeyId, secretKey);
        Region region = Region.of(regionKey);
        StaticCredentialsProvider credentials = StaticCredentialsProvider.create(awsCredentials);

        this.taggingClient = ResourceGroupsTaggingApiClient.builder()
                .region(region)
                .credentialsProvider(credentials) // Set the credentials
                .build();

        this.s3Client = S3Client.builder()
                .region(region)
                .credentialsProvider(credentials)
                .build();
        this.ec2Client = Ec2Client.builder()
                .region(region)
                .credentialsProvider(credentials)
                .build();
        this.lambdaClient = LambdaClient.builder()
                .region(region)
                .credentialsProvider(credentials)
                .build();
    }

    public List<String> getResourceTypes() {
        GetResourcesRequest request = GetResourcesRequest.builder().build();

        GetResourcesResponse response = taggingClient.getResources(request);

        var resp = response.resourceTagMappingList().stream()
                .map(mapping -> {
                    System.out.println(mapping.resourceARN());
                    var arnArr = mapping.resourceARN().split(":");
                    String resourceType = arnArr.length > 2 ? arnArr[2] : "Unknown";
                    return resourceType;
                })
                .distinct()
                .toList();
        return resp;
    }

    public Resource getMetadata(ResourceTypes resourceType) {
        switch (resourceType) {
            case ResourceTypes.EC2:
                return getMetadataForEC2();
            case ResourceTypes.LAMBDA:
                return getMetadataForLambda();
            case ResourceTypes.S3_BUCKET:
                return getMetadataForS3();
            default:
                throw new IllegalArgumentException("Resource type does not exit");
        }
    }

    private Ec2Vpc getMetadataForEC2() {
        DescribeVpcsRequest request = DescribeVpcsRequest.builder().build();

        DescribeVpcsResponse vpcsResponse = ec2Client.describeVpcs(request);
        var vpc = vpcsResponse.vpcs().get(0);
        return new Ec2Vpc(vpc.vpcId(), vpc.stateAsString(), vpc.cidrBlock(), vpc.isDefault(),
                ResourceTypes.EC2.getName());
    }

    private S3Bucket getMetadataForS3() {
        var response = getResourceResponse("s3");

        String bucketName = response.resourceTagMappingList().get(0).resourceARN().substring(13); // Extract bucket name
        GetBucketLocationResponse locationResponse = s3Client.getBucketLocation(
                GetBucketLocationRequest.builder().bucket(bucketName).build()
        );

        ListObjectsV2Request listRequest = ListObjectsV2Request.builder()
                .bucket(bucketName)
                .build();

        ListObjectsV2Response listResponse = s3Client.listObjectsV2(listRequest);
        var s3object = listResponse.contents().get(0); // FIXME: no contents

        return toS3Bucket(s3object, locationResponse.locationConstraintAsString());

    }

    private S3Bucket toS3Bucket(S3Object s3object, String locationConstraintAsString) {
        return new S3Bucket(s3object.size(), locationConstraintAsString, s3object.lastModified().toString(),
                ResourceTypes.S3_BUCKET.getName());
    }

    private Lambda getMetadataForLambda() {
        var resourceResponse = getResourceResponse("lambda");
        var functionName = resourceResponse.resourceTagMappingList().get(0).resourceARN().split(":")[6];

        GetFunctionRequest request = GetFunctionRequest.builder()
                .functionName(functionName)
                .build();

        GetFunctionResponse response = lambdaClient.getFunction(request);
        return toLambda(response.configuration());
    }

    private Lambda toLambda(FunctionConfiguration configuration) {
        return new Lambda(configuration.functionName(), configuration.lastModified(), configuration.runtimeAsString(),
                configuration.memorySize() + " MB", Integer.toString(configuration.timeout()),
                ResourceTypes.LAMBDA.getName());
    }

    private GetResourcesResponse getResourceResponse(String filter) {
        GetResourcesRequest request = GetResourcesRequest.builder().resourceTypeFilters(filter).build();
        return this.taggingClient.getResources(request);
    }
}
