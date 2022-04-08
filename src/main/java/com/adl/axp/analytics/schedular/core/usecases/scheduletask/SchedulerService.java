package com.adl.axp.analytics.schedular.core.usecases;

import org.springframework.scheduling.config.ScheduledTaskRegistrar;

public interface SchedulerService {
    public ScheduledTaskRegistrar addTriggerTasks(ScheduledTaskRegistrar scheduledTaskRegistrar);
}
