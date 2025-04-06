package com.example.backend_scaffold.infrastructure.security;

import com.example.backend_scaffold.domain.repository.UserRepository;
import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.io.Serializable;

/**
 * 权限评估器
 * <p>
 * 实现Spring Security的PermissionEvaluator接口，用于评估用户是否有权限执行特定操作
 * </p>
 *
 * @author example
 */
@Component
public class CustomPermissionEvaluator implements PermissionEvaluator {

    /**
     * 用户仓储
     */
    private final UserRepository userRepository;

    /**
     * 构造函数
     *
     * @param userRepository 用户仓储
     */
    public CustomPermissionEvaluator(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * 评估用户是否有权限访问指定对象
     *
     * @param authentication 认证信息
     * @param targetDomainObject 目标领域对象
     * @param permission 权限
     * @return 是否有权限
     */
    @Override
    public boolean hasPermission(Authentication authentication, Object targetDomainObject, Object permission) {
        if (authentication == null || targetDomainObject == null || !(permission instanceof String)) {
            return false;
        }

        // 获取用户名
        String username = authentication.getName();
        
        // 获取目标对象类型
        String targetType = targetDomainObject.getClass().getSimpleName().toLowerCase();
        
        // 构建权限字符串，例如：user:read
        String permissionString = targetType + ":" + permission.toString().toLowerCase();
        
        // 检查用户是否有指定权限
        return userRepository.hasPermission(username, permissionString);
    }

    /**
     * 评估用户是否有权限访问指定类型和ID的对象
     *
     * @param authentication 认证信息
     * @param targetId 目标ID
     * @param targetType 目标类型
     * @param permission 权限
     * @return 是否有权限
     */
    @Override
    public boolean hasPermission(Authentication authentication, Serializable targetId, String targetType, Object permission) {
        if (authentication == null || targetType == null || !(permission instanceof String)) {
            return false;
        }

        // 获取用户名
        String username = authentication.getName();
        
        // 构建权限字符串，例如：user:read
        String permissionString = targetType.toLowerCase() + ":" + permission.toString().toLowerCase();
        
        // 检查用户是否有指定权限
        return userRepository.hasPermission(username, permissionString);
    }
}