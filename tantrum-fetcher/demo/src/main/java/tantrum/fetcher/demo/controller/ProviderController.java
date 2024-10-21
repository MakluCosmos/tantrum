package tantrum.fetcher.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import tantrum.fetcher.demo.models.CloudProvider;
import tantrum.fetcher.demo.models.CloudProviders;
import tantrum.fetcher.demo.services.ResourceService;


@RestController()
@RequestMapping("/api")
public class ProviderController {

    private final ResourceService resourceService;

    @Autowired
    public ProviderController(ResourceService resourceService) {
        this.resourceService = resourceService;
    }

    @GetMapping("/cloud-providers")
    public CloudProvider getProviders() {
        return new CloudProvider(CloudProviders.AMAZON_WEB_SERVICES.getName(), this.resourceService.getResourceTypes());
    }

}
