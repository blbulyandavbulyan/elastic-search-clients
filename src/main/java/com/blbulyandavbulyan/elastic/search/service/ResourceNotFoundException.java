package com.blbulyandavbulyan.elastic.search.service;

public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String resourcePath) {
        super("Resource with name: " + resourcePath);
    }
}
