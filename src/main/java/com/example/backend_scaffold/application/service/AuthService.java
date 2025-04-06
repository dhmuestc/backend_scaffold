package com.example.backend_scaffold.application.service;

import com.example.backend_scaffold.application.dto.auth.AuthRequest;
import com.example.backend_scaffold.application.dto.auth.AuthResponse;
import com.example.backend_scaffold.application.dto.auth.PasswordChangeRequest;
import com.example.backend_scaffold.application.dto.auth.RegisterRequest;
import com.example.backend_scaffold.application.dto.user.UserResponse;

/**
 * 认证服务接口
 * <p>
 * 提供用户认证、注册、密码修改等功能
 * </p>
 *
 * @author example
 */
public interface AuthService {

    /**
     * 用户登录
     *
     * @param authRequest 认证请求DTO
     * @return 认证响应DTO，包含用户信息和JWT令牌
     */
    AuthResponse login(AuthRequest authRequest);

    /**
     * 用户注册
     *
     * @param registerRequest 注册请求DTO
     * @return 用户响应DTO
     */
    UserResponse register(RegisterRequest registerRequest);

    /**
     * 修改密码
     *
     * @param passwordChangeRequest 密码修改请求DTO
     * @param username              当前用户名
     * @return 是否修改成功
     */
    boolean changePassword(PasswordChangeRequest passwordChangeRequest, String username);

    /**
     * 刷新令牌
     *
     * @param refreshToken 刷新令牌
     * @return 新的认证响应DTO
     */
    AuthResponse refreshToken(String refreshToken);

    /**
     * 登出
     *
     * @param username 用户名
     */
    void logout(String username);
}