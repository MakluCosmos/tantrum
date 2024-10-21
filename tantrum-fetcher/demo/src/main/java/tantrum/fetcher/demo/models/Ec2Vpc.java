package tantrum.fetcher.demo.models;

public record Ec2Vpc(String Id, String state, String cidr, boolean isDefault, String resourceType) implements Resource {

}
