package com.blbulyandavbulyan.elastic.search.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;
import java.util.List;


public record Event(String title,
                    Type eventType,
                    LocalDateTime dateTime,
                    String place, String description,
                    List<String> subTopics) {

    @SuppressWarnings("unused") // used by Jackson deserializer
    public enum Type{
        @JsonProperty("workshop")
        WORKSHOP,
        @JsonProperty("tech-talk")
        TECH_TALK
    }
}
