package com.blbulyandavbulyan.elastic.search.client;

import com.blbulyandavbulyan.elastic.search.config.ElasticSearchIndexProperties;
import com.blbulyandavbulyan.elastic.search.model.Event;
import com.blbulyandavbulyan.elastic.search.service.resource.ElasticSearchRequest;
import com.blbulyandavbulyan.elastic.search.service.resource.ElasticSearchRequestTemplate;
import com.blbulyandavbulyan.elastic.search.service.resource.ResourceService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class EventElasticSearchLowLevelClient implements EventElasticSearchClient {
    @Qualifier("elasticSearchRestClient")
    private final RestClient restClient;
    private final ElasticSearchIndexProperties elasticSearchIndexProperties;
    private final ObjectMapper objectMapper;
    private final ResourceService resourceService;

    @Override
    public void createIndex() {
        restClient.put()
                .uri(uriBuilder -> uriBuilder.pathSegment(elasticSearchIndexProperties.getName()).build())
                .body(resourceService.loadResource(ElasticSearchRequest.CREATE_EVENTS_INDEX))
                .retrieve();
    }

    @Override
    public void applyMapping(Mapping mapping) {
        restClient.put()
                .uri("/{indexName}", elasticSearchIndexProperties.getName())
                .body(Map.of("mappings", mapping.getElasticSearchProperties()));
    }

    @Override
    public void save(Event object) {
        restClient.post()
                .uri("/{indexName}/_doc", elasticSearchIndexProperties.getName())
                .body(object)
                .retrieve();
    }

    private List<Event> searchEvents(String searchQuery) {
        return restClient.post()
                .uri("/{indexName}/_search", elasticSearchIndexProperties.getName())
                .contentType(MediaType.APPLICATION_JSON)
                .body(searchQuery)
                .retrieve()
                .body(SearchEventResponse.class)
                .hits().hits().stream()
                .map(SearchEventResponse.Hit::source)
                .toList();
    }

    @Override
    public List<Event> findAll() {
        return searchEvents(resourceService.loadResource(ElasticSearchRequest.FIND_ALL));
    }

    @Override
    @SneakyThrows
    public List<Event> findByEventType(Event.Type type) {
        return searchEvents(resourceService.loadResource(ElasticSearchRequestTemplate.FIND_BY_EVENT_TYPE)
                .formatted(objectMapper.writeValueAsString(type)));
    }

    @Override
    @SneakyThrows
    public List<Event> findByTitle(String title) {
        return searchEvents(resourceService.loadResource(ElasticSearchRequestTemplate.FIND_BY_TITLE)
                .formatted(objectMapper.writeValueAsString(title)));
    }

    @Override
    @SneakyThrows
    public List<Event> findByTitleAfterDateTime(String title, ZonedDateTime dateTime) {
        return searchEvents(resourceService.loadResource(ElasticSearchRequestTemplate.FIND_BY_TITLE_AFTER_DATE)
                .formatted(objectMapper.writeValueAsString(title),
                objectMapper.writeValueAsString(dateTime)));
    }
}
