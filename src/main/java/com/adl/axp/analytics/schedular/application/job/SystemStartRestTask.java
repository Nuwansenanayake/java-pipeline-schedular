package com.adl.axp.analytics.schedular.infrastructure.job;

import com.adl.axp.analytics.schedular.core.usecases.startuptask.StartupTask;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Service;

@Service
@EnableScheduling
public class SystemStartRestTask {


    @Autowired
    StartupTask startupTask;

    @Bean
    public void configureTasks() {

        startupTask.configureTasks();



    }


}
