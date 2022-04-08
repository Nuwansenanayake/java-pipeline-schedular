package com.adl.axp.analytics.schedular.schedular2.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties("log")
@Getter
@Setter
public class LogPropertyConfig {
    private boolean elasticEnabled = false;
    private String elasticbodyfilepath = "";
    private String elasticuri = "";
    private String elasticindex = "";
}
