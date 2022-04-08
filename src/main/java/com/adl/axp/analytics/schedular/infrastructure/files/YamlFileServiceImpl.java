package com.adl.axp.analytics.schedular.infrastructure.repository;

import com.adl.axp.analytics.schedular.core.ports.YamlFileService;
import com.adl.axp.analytics.schedular.core.domain.RestInfo;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Service
public class YamlFileServiceImpl implements  YamlFileService {
    static Logger LOGGER  = LoggerFactory.getLogger(YamlFileServiceImpl.class);

    @Autowired
    File restInfoFile;

    @Override
    @Bean
    public  List<RestInfo> readYaml()  {
        ObjectMapper objectMapper = new ObjectMapper(new YAMLFactory());
        List<RestInfo> restInfoList=null;
        try {
            restInfoList= objectMapper.readValue(restInfoFile, new TypeReference<List<RestInfo>>(){});
        } catch (IOException e) {
            LOGGER.error("restinfo.yml file error, check its available and has data");
        }
        return restInfoList;
    }


}
