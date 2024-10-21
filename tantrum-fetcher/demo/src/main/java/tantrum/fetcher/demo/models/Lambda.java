package tantrum.fetcher.demo.models;

public record Lambda(String functionName, String lastModified, String runtime, String memorySize, String timeout, String resourceType) implements Resource {

}
