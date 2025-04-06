package com.example.backend_scaffold.interfaces.api;

import com.example.backend_scaffold.application.dto.auth.AuthRequest;
import com.example.backend_scaffold.application.dto.auth.AuthResponse;
import com.example.backend_scaffold.application.dto.auth.PasswordChangeRequest;
import com.example.backend_scaffold.application.dto.auth.RegisterRequest;
import com.example.backend_scaffold.application.dto.user.UserResponse;
import com.example.backend_scaffold.interfaces.facade.AuthFacade;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

/**
 * 认证控制器
 * <p>
 * 提供用户认证、注册、密码修改等功能的API接口
 * </p>
 *
 * @author example
 */
@RestController
@RequestMapping("/api/auth")
@Tag(name = "认证管理", description = "认证管理相关接口")
@RequiredArgsConstructor
public class AuthController {

    private final AuthFacade authFacade;

    /**
     * 用户登录
     *
     * @param authRequest 认证请求DTO
     * @return 认证响应DTO，包含用户信息和JWT令牌
     */
    @PostMapping("/login")
    @Operation(summary = "用户登录", description = "用户登录认证并返回JWT令牌")
    public AuthResponse login(@Valid @RequestBody AuthRequest authRequest) {
        return authFacade.login(authRequest);
    }

    /**
     * 用户注册
     *
     * @param registerRequest 注册请求DTO
     * @return 用户响应DTO
     */
    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "用户注册", description = "注册新用户")
    public UserResponse register(@Valid @RequestBody RegisterRequest registerRequest) {
        return authFacade.register(registerRequest);
    }

    /**
     * 修改密码
     *
     * @param passwordChangeRequest 密码修改请求DTO
     * @return 是否修改成功
     */
    @PostMapping("/change-password")
    @Operation(summary = "修改密码", description = "修改当前登录用户的密码")
    @PreAuthorize("isAuthenticated()")
    public boolean changePassword(@Valid @RequestBody PasswordChangeRequest passwordChangeRequest) {
        return authFacade.changePassword(passwordChangeRequest);
    }

    /**
     * 刷新令牌
     *
     * @param refreshToken 刷新令牌
     * @return 新的认证响应DTO
     */
    @PostMapping("/refresh-token")
    @Operation(summary = "刷新令牌", description = "使用刷新令牌获取新的访问令牌")
    public AuthResponse refreshToken(@RequestParam String refreshToken) {
        return authFacade.refreshToken(refreshToken);
    }

    /**
     * 登出
     */
    @PostMapping("/logout")
    @Operation(summary = "用户登出", description = "当前用户登出系统")
    @PreAuthorize("isAuthenticated()")
    public void logout() {
        authFacade.logout();
    }
}