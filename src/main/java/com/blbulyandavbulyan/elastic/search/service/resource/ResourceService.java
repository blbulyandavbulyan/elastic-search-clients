package com.blbulyandavbulyan.elastic.search.service.resource;

import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;

@Service
public class ResourceService {
    @SneakyThrows
    public String loadResource(Resource resource) {
        final String resourceName = resource.getResourceName();
        try (InputStream resourceAsStream = getClass().getResourceAsStream(resourceName)) {
            if (resourceAsStream == null) {
                throw new ResourceNotFoundException(resourceName);
            }
            return new String(resourceAsStream.readAllBytes(), StandardCharsets.UTF_8);
        }
    }
}
