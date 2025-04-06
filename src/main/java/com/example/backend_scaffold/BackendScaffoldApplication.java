package com.example.backend_scaffold;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * 后端脚手架应用程序入口类
 * <p>
 * 这是一个基于Spring Boot的应用程序，采用DDD架构设计，
 * 实现了RBAC权限模型，用于快速构建企业级应用的后端系统。
 * </p>
 *
 * @author example
 */
@SpringBootApplication

@EnableCaching
@EnableAsync
public class BackendScaffoldApplication {

    public static void main(String[] args) {
        SpringApplication.run(BackendScaffoldApplication.class, args);
    }

}