package com.blbulyandavbulyan.elastic.search.service.resource;

import lombok.Getter;

@Getter
public enum ElasticSearchRequestTemplate implements Resource{
    FIND_BY_TITLE_AFTER_DATE("/elastic-search/requests/search/templates/find-by-title-after-date-time.jsont"),
    FIND_BY_EVENT_TYPE("/elastic-search/requests/search/templates/find-by-event-type.jsont"),
    FIND_BY_TITLE("/elastic-search/requests/search/templates/find-by-title.jsont");
    private final String resourceName;

    ElasticSearchRequestTemplate(String resourceName) {
        this.resourceName = resourceName;
    }
}
