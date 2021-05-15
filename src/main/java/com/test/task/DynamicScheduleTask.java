package com.test.task;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.Trigger;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.concurrent.ScheduledFuture;

@Component
public abstract class DynamicScheduleTask {

    @PostConstruct
    public void init() {
        startTask();
    }

    @Autowired
    private TaskScheduler scheduledExecutorService;

    private ScheduledFuture<?> future;

    public abstract String getCron();

    public abstract void doTask();

    public void startTask() {
        future = scheduledExecutorService.schedule(getRunnable(), getTrigger());
    }

    public void endTask() {
        if (future != null) {
            future.cancel(true);
        }
    }

    private Runnable getRunnable() {
        return () -> doTask();
    }

    private Trigger getTrigger() {
        return (triggerContext) -> {
            CronTrigger trigger = new CronTrigger(getCron());
            return trigger.nextExecutionTime(triggerContext);
        };
    }

}
