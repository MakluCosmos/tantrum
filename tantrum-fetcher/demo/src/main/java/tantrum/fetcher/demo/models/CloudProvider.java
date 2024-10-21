package tantrum.fetcher.demo.models;

import java.util.List;

public class CloudProvider {

private String name;
private List<String> resourceTypes;

public CloudProvider(String name, List<String> resourceTypes) {
    this.name = name;
    this.resourceTypes = resourceTypes;
}

public String getName() {
    return name;
}
public void setName(CloudProviders cloudProvider) {
    this.name = cloudProvider.getName();
}

public List<String> getResourceTypes() {
    return resourceTypes;
}
public void setResourceTypes(List<String> resourceTypes) {
    this.resourceTypes = resourceTypes;
}

}
