package com.test.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * 自定义spring线程池
 */
@Configuration
public class ExecutorConfig {

    @Bean("taskExecutor")
    public Executor taskExecutor() {
        int processors = Runtime.getRuntime().availableProcessors();
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        //配置核心线程数
        executor.setCorePoolSize(processors * 2);
        //配置最大线程数
        executor.setMaxPoolSize(processors * 4);
        //配置队列大小
        executor.setQueueCapacity(processors * 4);
        //配置线程池中的线程的名称前缀
        executor.setThreadNamePrefix("async-service-");
        executor.setKeepAliveSeconds(30);

        // rejection-policy：当pool已经达到max size的时候，如何处理新任务
        // CALLER_RUNS：不在新线程中执行任务，而是有调用者所在的线程来执行
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        //执行初始化
        executor.initialize();
        return executor;
    }

    @Bean
    public TaskScheduler scheduledExecutorService() {
        ThreadPoolTaskScheduler scheduler = new ThreadPoolTaskScheduler();
        int processors = Runtime.getRuntime().availableProcessors();
        scheduler.setPoolSize(processors * 2);
        scheduler.setThreadNamePrefix("scheduled-thread-");
        return scheduler;
    }
}