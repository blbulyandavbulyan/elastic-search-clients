package com.blbulyandavbulyan.elastic.search.service.resource;

public sealed interface Resource permits ElasticSearchRequest, ElasticSearchRequestTemplate {
    String getResourceName();
}
