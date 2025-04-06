package com.example.backend_scaffold.application.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

/**
 * 异步配置类
 * <p>
 * 配置Spring异步执行功能，提供线程池执行器
 * </p>
 *
 * @author example
 */
@Configuration
@EnableAsync
public class AsyncConfig {

    /**
     * 异步任务执行器
     * <p>
     * 配置线程池参数，用于执行异步任务
     * </p>
     *
     * @return 线程池执行器
     */
    @Bean(name = "taskExecutor")
    public Executor taskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        // 核心线程数：线程池创建时初始化的线程数
        executor.setCorePoolSize(5);
        // 最大线程数：线程池最大的线程数，只有在队列满了之后才会申请超过核心线程数的线程
        executor.setMaxPoolSize(10);
        // 队列容量：用来缓冲执行任务的队列
        executor.setQueueCapacity(25);
        // 线程池名称前缀
        executor.setThreadNamePrefix("Async-");
        // 等待所有任务结束后再关闭线程池
        executor.setWaitForTasksToCompleteOnShutdown(true);
        // 初始化
        executor.initialize();
        return executor;
    }
}