package com.blbulyandavbulyan.elastic.search.service;

import com.blbulyandavbulyan.elastic.search.client.EventElasticSearchClient;
import com.blbulyandavbulyan.elastic.search.controller.EventQuery;
import com.blbulyandavbulyan.elastic.search.model.Event;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EventSearchService {
    private final EventElasticSearchClient eventElasticSearchClient;

    public List<Event> searchEvents(EventQuery eventQuery) {
        if (eventQuery.title() != null && eventQuery.afterDateTime() != null) {
            return eventElasticSearchClient.findByTitleAfterDateTime(eventQuery.title(), eventQuery.afterDateTime());
        } else if (eventQuery.title() != null) {
            return eventElasticSearchClient.findByTitle(eventQuery.title());
        } else if (eventQuery.type() != null) {
            return eventElasticSearchClient.findByEventType(eventQuery.type());
        } else {
            return eventElasticSearchClient.findAll();
        }
    }
}
