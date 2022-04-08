package com.adl.axp.analytics.schedular2.service;

import com.adl.axp.analytics.schedular.application.ports.RestService;
import com.adl.axp.analytics.schedular.adaptor.RestServiceImpl;
import com.adl.axp.analytics.schedular.application.usecases.MakeRestCallUsercase;
import com.adl.axp.analytics.schedular.application.usecases.MakeRestCallUsercaseImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringConfig {
    @Bean
    public RestService restService(){
        return new RestServiceImpl();
    }

    @Bean
    public MakeRestCallUsercase makeRestCallUsercase(){
        return new MakeRestCallUsercaseImpl(restService());
    }
}
