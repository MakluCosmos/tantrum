package tantrum.fetcher.demo.models;

public enum CloudProviders {

    GOOGLE_CLOUD_PLATFORM("GCP"),
    AMAZON_WEB_SERVICES("AWS"),
    AZURE_CLOUD("Azure");

    private final String name;

    public String getName() {
        return name;
    }

    private CloudProviders(String name) {
        this.name = name;
    }
}
