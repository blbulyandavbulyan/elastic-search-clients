package com.blbulyandavbulyan.elastic.search.client;

import com.blbulyandavbulyan.elastic.search.model.Event;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record SearchEventResponse(Hits hits) {
    public record Hits(List<Hit> hits){
    }
    public record Hit(@JsonProperty("_source") Event source){
    }
}
