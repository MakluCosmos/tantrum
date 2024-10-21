package tantrum.fetcher.demo.models;

public record S3Bucket(Long size, String Location, String creationDate, String resourceType) implements Resource {

}
