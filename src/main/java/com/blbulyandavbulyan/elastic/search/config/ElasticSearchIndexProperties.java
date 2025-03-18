package com.blbulyandavbulyan.elastic.search.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "elasticsearch.index")
@Getter
@Setter
public class ElasticSearchIndexProperties {
    private String name = "events";
}
