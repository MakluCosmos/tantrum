package tantrum.fetcher.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import tantrum.fetcher.demo.models.CloudProvider;
import tantrum.fetcher.demo.models.CloudProviders;
import tantrum.fetcher.demo.models.Resource;
import tantrum.fetcher.demo.models.ResourceTypes;
import tantrum.fetcher.demo.services.ResourceService;

@RestController()
@RequestMapping("/api")
public class ResourceController {
    
        private final ResourceService resourceService;
    
        @Autowired
        public ResourceController(ResourceService resourceService) {
            this.resourceService = resourceService;
        }
    
        @GetMapping("/resource-types")
        public CloudProvider getProviders() {
            return new CloudProvider(CloudProviders.AMAZON_WEB_SERVICES.getName(), this.resourceService.getResourceTypes());
        }

        @GetMapping("/resources/metadata/{type}")
        public Resource getProviders(@PathVariable() String type) {
            return resourceService.getMetadata(ResourceTypes.fromValue(type));
        }
    
    }