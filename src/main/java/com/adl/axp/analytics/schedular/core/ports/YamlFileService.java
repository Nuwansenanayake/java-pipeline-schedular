package com.adl.axp.analytics.schedular.application.ports;

import com.adl.axp.analytics.schedular.domain.RestInfo;
import org.springframework.context.annotation.Bean;

import java.util.List;

public interface YamlFileService {
    @Bean
    List<RestInfo> readYaml();
}
