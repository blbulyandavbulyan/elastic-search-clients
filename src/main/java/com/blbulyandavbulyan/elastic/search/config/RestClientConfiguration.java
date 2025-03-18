package com.blbulyandavbulyan.elastic.search.config;

import org.springframework.boot.autoconfigure.web.client.RestClientSsl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.web.client.RestClient;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

@Configuration
public class RestClientConfiguration {
    @Bean
    public RestClient elasticSearchRestClient(ElasticSearchProperties properties, RestClientSsl restClientSsl) {
        Base64.Encoder encoder = Base64.getEncoder();
        String userNamePassword = "%s:%s".formatted(properties.getUsername(), properties.getPassword());
        return RestClient.builder()
                .baseUrl(properties.getUrl())
                .defaultHeaders(httpHeaders -> httpHeaders.add(HttpHeaders.AUTHORIZATION, "Basic " + encoder.encodeToString(userNamePassword.getBytes(StandardCharsets.US_ASCII))))
                .apply(restClientSsl.fromBundle("elasticsearch"))
                .build();
    }
}
