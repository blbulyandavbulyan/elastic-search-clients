package com.blbulyandavbulyan.elastic.search.client;

import com.blbulyandavbulyan.elastic.search.config.ElasticSearchIndexProperties;
import com.blbulyandavbulyan.elastic.search.model.Event;
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

    @Override
    public void createIndex() {
        restClient.put()
                .uri(uriBuilder -> uriBuilder.pathSegment(elasticSearchIndexProperties.getName()).build())
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
                .uri("/{indexName}", elasticSearchIndexProperties.getName())
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
        return searchEvents("""
                        {
                          "query": {
                            "match_all": {}
                          }
                        }
                        """);
    }

    @Override
    @SneakyThrows
    public List<Event> findByEventType(Event.Type type) {
        return searchEvents("""
                        {
                          "query": {
                            "term": {
                              "eventType.keyword": {
                                "value": %s
                              }
                            }
                          }
                        }
                        """.formatted(objectMapper.writeValueAsString(type)));
    }

    @Override
    @SneakyThrows
    public List<Event> findByTitle(String title) {
        return searchEvents("""
                {
                  "query": {
                    "match": {
                      "title": %s
                    }
                  }
                }
                """.formatted(objectMapper.writeValueAsString(title)));
    }

    @Override
    @SneakyThrows
    public List<Event> findByTitleAfterDateTime(String title, ZonedDateTime dateTime) {
        return searchEvents("""
                {
                  "query": {
                    "bool": {
                      "must": [
                        {"match": {
                          "title": %s
                        }}
                      ],
                      "filter": [
                        {"range": {
                          "dateTime": {
                            "gt": %s
                          }
                        }}
                      ]
                    }
                  }
                }
                """.formatted(objectMapper.writeValueAsString(title),
                objectMapper.writeValueAsString(dateTime)));
    }
}
