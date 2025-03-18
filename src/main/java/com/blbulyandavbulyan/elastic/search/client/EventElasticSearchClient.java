package com.blbulyandavbulyan.elastic.search.client;

import com.blbulyandavbulyan.elastic.search.model.Event;

import java.time.ZonedDateTime;
import java.util.List;

public interface EventElasticSearchClient {
    void createIndex();

    void applyMapping(Mapping mapping);

    void save(Event object);

    List<Event> findAll();

    List<Event> findByEventType(Event.Type type);

    List<Event> findByTitle(String title);

    List<Event> findByTitleAfterDateTime(String title, ZonedDateTime dateTime);
}
