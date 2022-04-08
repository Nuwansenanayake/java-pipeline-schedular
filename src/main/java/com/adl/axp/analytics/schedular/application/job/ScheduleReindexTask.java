package com.adl.axp.analytics.schedular.infrastructure.job;

import com.adl.axp.analytics.schedular.core.usecases.scheduletask.SchedulerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.stereotype.Service;

@Service
@EnableScheduling
public class ScheduleReindexTask implements SchedulingConfigurer {
    @Autowired
    TaskScheduler taskScheduler;
    @Autowired
    SchedulerService schedulerService;



    @Override
    public void configureTasks(ScheduledTaskRegistrar scheduledTaskRegistrar) {
        scheduledTaskRegistrar.setScheduler(taskScheduler);
        scheduledTaskRegistrar=schedulerService.addTriggerTasks(scheduledTaskRegistrar);

    }
}
