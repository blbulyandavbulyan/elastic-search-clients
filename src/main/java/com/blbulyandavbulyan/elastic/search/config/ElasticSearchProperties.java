package com.blbulyandavbulyan.elastic.search.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "elasticsearch")
@Getter
@Setter
public class ElasticSearchProperties {
    private String url;
    private String username;
    private String password;
}
