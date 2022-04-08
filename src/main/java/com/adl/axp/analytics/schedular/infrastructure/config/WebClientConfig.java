package com.adl.axp.analytics.schedular.schedular2.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.util.Base64Utils;
import org.springframework.web.reactive.function.client.WebClient;

import java.nio.charset.StandardCharsets;

@Configuration
public class WebClientConfig {
    private static Logger LOGGER = LoggerFactory.getLogger(WebClientConfig.class);
    @Autowired
    private RestClientConfig conf;

    @Bean
    public WebClient webClientConfigured(){
        return WebClient.builder()
                .baseUrl(conf.getBaseurl())
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .defaultHeader("Authorization", "Basic "+ Base64Utils.encodeToString((conf.getUsername()+":"+conf.getPassword()).getBytes(StandardCharsets.UTF_8)))
                .build();
    }
}
