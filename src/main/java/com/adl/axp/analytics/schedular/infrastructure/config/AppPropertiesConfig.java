package com.adl.axp.analytics.schedular.schedular2.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties("app")
@Getter
@Setter
public class AppPropertiesConfig {
    private boolean logappdebuglogs=false;
    private boolean mockrestcalls=false;
}
