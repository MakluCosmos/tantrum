package tantrum.fetcher.demo.models;

public enum ResourceTypes {

    EC2("ec2"),
    S3_BUCKET("s3"),
    LAMBDA("lambda");

    private final String name;

    public String getName() {
        return name;
    }

    private ResourceTypes(String name) {
        this.name = name;
    }

    public static ResourceTypes fromValue(String value) {
        for (ResourceTypes type : ResourceTypes.values()) {
            if (type.getName().equalsIgnoreCase(value)) {
                return type;
            }
        }
        throw new IllegalArgumentException("Unknown resource type: " + value);
    }
}
