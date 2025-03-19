package com.blbulyandavbulyan.elastic.search.service.resource;

import lombok.Getter;

@Getter
public enum ElasticSearchRequest implements Resource {
    CREATE_EVENTS_INDEX("/elastic-search/requests/index/events-create-index-request.json"),
    FIND_ALL("/elastic-search/requests/search/find-all.json");

    private final String resourceName;

    ElasticSearchRequest(String resourceName) {
        this.resourceName = resourceName;
    }
}
