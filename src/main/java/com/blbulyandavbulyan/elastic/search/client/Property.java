package com.blbulyandavbulyan.elastic.search.client;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

@Builder
public record Property(String name, Type type) {
    enum Type {
        @JsonProperty("integer")
        INTEGER,
        @JsonProperty("text")
        TEXT,
        @JsonProperty("keyword")
        KEYWORD,
        @JsonProperty("date")
        DATE
    }
    public record Config(Type type){
    }

    public Config toConfig(){
        return new Config(type);
    }
}
