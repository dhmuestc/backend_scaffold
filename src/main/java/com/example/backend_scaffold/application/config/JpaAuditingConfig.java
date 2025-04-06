package com.example.backend_scaffold.application.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

/**
 * JPA审计配置类
 * <p>
 * 用于自动填充实体的创建时间、创建人、更新时间和更新人等审计字段
 * </p>
 *
 * @author example
 */
@Configuration
public class JpaAuditingConfig {

    /**
     * 审计人提供者
     * <p>
     * 返回当前登录用户的用户名作为审计人
     * </p>
     *
     * @return 当前登录用户的用户名
     */
    @Bean
    public AuditorAware<String> auditorProvider() {
        return () -> {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (authentication == null || !authentication.isAuthenticated()) {
                return Optional.of("system");
            }
            return Optional.of(authentication.getName());
        };
    }
}