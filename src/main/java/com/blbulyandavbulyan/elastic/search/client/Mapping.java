package com.blbulyandavbulyan.elastic.search.client;

import lombok.Builder;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Builder
public record Mapping(List<Property> properties){
    Map<String, Property.Config> getElasticSearchProperties() {
        return properties()
                .stream()
                .collect(Collectors.toMap(Property::name, Property::toConfig));
    }
}
