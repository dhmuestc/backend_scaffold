package com.example.backend_scaffold.interfaces.facade;

import com.example.backend_scaffold.application.dto.auth.AuthRequest;
import com.example.backend_scaffold.application.dto.auth.AuthResponse;
import com.example.backend_scaffold.application.dto.auth.PasswordChangeRequest;
import com.example.backend_scaffold.application.dto.auth.RegisterRequest;
import com.example.backend_scaffold.application.dto.user.UserResponse;
import com.example.backend_scaffold.application.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

/**
 * 认证外观类，作为应用服务的门面
 */
@Component
@RequiredArgsConstructor
public class AuthFacade {

    private final AuthService authService;

    /**
     * 用户登录
     *
     * @param authRequest 认证请求DTO
     * @return 认证响应DTO，包含用户信息和JWT令牌
     */
    public AuthResponse login(AuthRequest authRequest) {
        return authService.login(authRequest);
    }

    /**
     * 用户注册
     *
     * @param registerRequest 注册请求DTO
     * @return 用户响应DTO
     */
    public UserResponse register(RegisterRequest registerRequest) {
        return authService.register(registerRequest);
    }

    /**
     * 修改密码
     *
     * @param passwordChangeRequest 密码修改请求DTO
     * @return 是否修改成功
     */
    public boolean changePassword(PasswordChangeRequest passwordChangeRequest) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        return authService.changePassword(passwordChangeRequest, username);
    }

    /**
     * 刷新令牌
     *
     * @param refreshToken 刷新令牌
     * @return 新的认证响应DTO
     */
    public AuthResponse refreshToken(String refreshToken) {
        return authService.refreshToken(refreshToken);
    }

    /**
     * 登出
     */
    public void logout() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        authService.logout(username);
    }
}