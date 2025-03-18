package com.blbulyandavbulyan.elastic.search.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.ZonedDateTime;
import java.util.List;


public record Event(String title,
                    Type eventType,
                    ZonedDateTime dateTime,
                    String place, String description,
                    List<String> subtopics) {
    public enum Type{
        @JsonProperty("workshop")
        WORKSHOP,
        @JsonProperty("tech-talk")
        TECH_TALK
    }
}
