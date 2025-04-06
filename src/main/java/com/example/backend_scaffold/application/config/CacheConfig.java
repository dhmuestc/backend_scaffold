package com.example.backend_scaffold.application.config;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 缓存配置类
 * <p>
 * 配置Spring缓存功能，提供缓存管理器
 * </p>
 *
 * @author example
 */
@Configuration
@EnableCaching
public class CacheConfig {

    /**
     * 缓存管理器
     * <p>
     * 使用ConcurrentMapCacheManager作为默认的缓存管理器
     * 在生产环境中，可以替换为Redis或其他分布式缓存
     * </p>
     *
     * @return 缓存管理器
     */
    @Bean
    public CacheManager cacheManager() {
        // 创建基于ConcurrentMap的缓存管理器
        ConcurrentMapCacheManager cacheManager = new ConcurrentMapCacheManager();
        
        // 设置缓存名称，与@Cacheable注解中的value对应
        cacheManager.setCacheNames(java.util.Arrays.asList(
            "users", 
            "roles", 
            "permissions"
        ));
        
        return cacheManager;
    }
}