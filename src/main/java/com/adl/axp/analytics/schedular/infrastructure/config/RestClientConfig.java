package com.adl.axp.analytics.schedular.schedular2.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties("rest")
@Getter
@Setter
public class RestClientConfig  {
    private String baseurl;
    private String username;
    private String password;
    private boolean logappdebuglogs=false;
}
