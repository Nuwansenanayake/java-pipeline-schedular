package com.adl.axp.analytics.schedular.schedular2.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.File;

@Configuration
public class YamlFileConfig {

    @Bean
    public File restInfoFile(){
        return new File("config/restinfo.yaml");
    }
}
