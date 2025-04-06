package com.example.backend_scaffold.application.service;

import com.example.backend_scaffold.application.dto.auth.AuthRequest;
import com.example.backend_scaffold.application.dto.auth.AuthResponse;
import com.example.backend_scaffold.application.dto.auth.PasswordChangeRequest;
import com.example.backend_scaffold.application.dto.auth.RegisterRequest;
import com.example.backend_scaffold.application.dto.user.UserResponse;
import com.example.backend_scaffold.application.exception.BusinessException;
import com.example.backend_scaffold.application.exception.EntityNotFoundException;
import com.example.backend_scaffold.application.exception.UnauthorizedException;
import com.example.backend_scaffold.application.mapper.UserMapper;
import com.example.backend_scaffold.application.service.impl.AuthServiceImpl;
import com.example.backend_scaffold.domain.model.entity.UserEntity;
import com.example.backend_scaffold.domain.model.enums.UserStatus;
import com.example.backend_scaffold.domain.service.UserDomainService;
import com.example.backend_scaffold.infrastructure.cache.CacheService;
import com.example.backend_scaffold.infrastructure.security.JwtTokenProvider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AuthServiceTest {

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private JwtTokenProvider jwtTokenProvider;

    @Mock
    private UserDomainService userDomainService;

    @Mock
    private UserMapper userMapper;

    @Mock
    private CacheService cacheService;

    @Mock
    private Authentication authentication;

    @InjectMocks
    private AuthServiceImpl authService;

    private UserEntity testUser;
    private AuthRequest authRequest;
    private RegisterRequest registerRequest;
    private PasswordChangeRequest passwordChangeRequest;

    @BeforeEach
    void setUp() {
        // 创建测试用户
        testUser = new UserEntity();
        testUser.setId(1L);
        testUser.setUsername("testuser");
        testUser.setPassword("password");
        testUser.setEmail("test@example.com");
        testUser.setStatus(UserStatus.ACTIVE);

        // 创建认证请求
        authRequest = new AuthRequest();
        authRequest.setUsername("testuser");
        authRequest.setPassword("password");

        // 创建注册请求
        registerRequest = new RegisterRequest();
        registerRequest.setUsername("newuser");
        registerRequest.setPassword("password");
        registerRequest.setConfirmPassword("password");
        registerRequest.setEmail("new@example.com");

        // 创建密码修改请求
        passwordChangeRequest = new PasswordChangeRequest();
        passwordChangeRequest.setOldPassword("oldpassword");
        passwordChangeRequest.setNewPassword("newpassword");
        passwordChangeRequest.setConfirmPassword("newpassword");

        // 清除安全上下文
        SecurityContextHolder.clearContext();
    }

    @Test
    @DisplayName("测试登录成功")
    void testLoginSuccess() {
        // 准备模拟数据
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenReturn(authentication);
        when(authentication.getAuthorities()).thenAnswer(invocation ->
                Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER")));
        when(jwtTokenProvider.createToken(any(Authentication.class))).thenReturn("jwt-token");
        when(userDomainService.findByUsername("testuser")).thenReturn(Optional.of(testUser));

        // 执行测试
        AuthResponse response = authService.login(authRequest);

        // 验证结果
        assertNotNull(response);
        assertEquals("jwt-token", response.getToken());
        assertEquals("Bearer", response.getTokenType());
        assertEquals(1L, response.getUserId());
        assertEquals("testuser", response.getUsername());
        assertEquals(1, response.getRoles().size());
        assertEquals("ROLE_USER", response.getRoles().get(0));

        // 验证方法调用
        verify(authenticationManager).authenticate(any(UsernamePasswordAuthenticationToken.class));
        verify(jwtTokenProvider).createToken(any(Authentication.class));
        verify(userDomainService).findByUsername("testuser");
    }

    @Test
    @DisplayName("测试登录失败 - 认证异常")
    void testLoginFailureAuthenticationException() {
        // 准备模拟数据
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenThrow(new AuthenticationException("Authentication failed") {});

        // 执行测试并验证异常
        assertThrows(UnauthorizedException.class, () -> authService.login(authRequest));

        // 验证方法调用
        verify(authenticationManager).authenticate(any(UsernamePasswordAuthenticationToken.class));
        verify(jwtTokenProvider, never()).createToken(any(Authentication.class));
        verify(userDomainService, never()).findByUsername(anyString());
    }

    @Test
    @DisplayName("测试登录失败 - 用户不存在")
    void testLoginFailureUserNotFound() {
        // 准备模拟数据
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenReturn(authentication);
        when(authentication.getAuthorities()).thenAnswer(invocation ->
                Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER")));
        when(jwtTokenProvider.createToken(any(Authentication.class))).thenReturn("jwt-token");
        when(userDomainService.findByUsername("testuser")).thenReturn(Optional.empty());

        // 执行测试并验证异常
        assertThrows(EntityNotFoundException.class, () -> authService.login(authRequest));

        // 验证方法调用
        verify(authenticationManager).authenticate(any(UsernamePasswordAuthenticationToken.class));
        verify(jwtTokenProvider).createToken(any(Authentication.class));
        verify(userDomainService).findByUsername("testuser");
    }

    @Test
    @DisplayName("测试注册成功")
    void testRegisterSuccess() {
        // 准备模拟数据
        when(userDomainService.findByUsername("newuser")).thenReturn(Optional.empty());
        when(userDomainService.findByEmail("new@example.com")).thenReturn(Optional.empty());
        when(userDomainService.createUser(any(UserEntity.class))).thenReturn(testUser);
        when(userMapper.toResponse(any())).thenReturn(new UserResponse());

        // 执行测试
        UserResponse response = authService.register(registerRequest);

        // 验证结果
        assertNotNull(response);

        // 验证方法调用
        verify(userDomainService).findByUsername("newuser");
        verify(userDomainService).findByEmail("new@example.com");
        verify(userDomainService).createUser(any(UserEntity.class));
        verify(userMapper).toResponse(any());
    }

    @Test
    @DisplayName("测试注册失败 - 密码不匹配")
    void testRegisterFailurePasswordMismatch() {
        // 修改注册请求使密码不匹配
        registerRequest.setConfirmPassword("different");

        // 执行测试并验证异常
        assertThrows(BusinessException.class, () -> authService.register(registerRequest));

        // 验证方法调用
        verify(userDomainService, never()).findByUsername(anyString());
        verify(userDomainService, never()).findByEmail(anyString());
        verify(userDomainService, never()).createUser(any(UserEntity.class));
    }

    @Test
    @DisplayName("测试注册失败 - 用户名已存在")
    void testRegisterFailureUsernameExists() {
        // 准备模拟数据
        when(userDomainService.findByUsername("newuser")).thenReturn(Optional.of(testUser));

        // 执行测试并验证异常
        assertThrows(BusinessException.class, () -> authService.register(registerRequest));

        // 验证方法调用
        verify(userDomainService).findByUsername("newuser");
        verify(userDomainService, never()).findByEmail(anyString());
        verify(userDomainService, never()).createUser(any(UserEntity.class));
    }

    @Test
    @DisplayName("测试注册失败 - 邮箱已存在")
    void testRegisterFailureEmailExists() {
        // 准备模拟数据
        when(userDomainService.findByUsername("newuser")).thenReturn(Optional.empty());
        when(userDomainService.findByEmail("new@example.com")).thenReturn(Optional.of(testUser));

        // 执行测试并验证异常
        assertThrows(BusinessException.class, () -> authService.register(registerRequest));

        // 验证方法调用
        verify(userDomainService).findByUsername("newuser");
        verify(userDomainService).findByEmail("new@example.com");
        verify(userDomainService, never()).createUser(any(UserEntity.class));
    }

    @Test
    @DisplayName("测试修改密码成功")
    void testChangePasswordSuccess() {
        // 准备模拟数据
        when(userDomainService.findByUsername("testuser")).thenReturn(Optional.of(testUser));
        when(userDomainService.changePassword(eq(1L), anyString(), anyString())).thenReturn(true);

        // 执行测试
        boolean result = authService.changePassword(passwordChangeRequest, "testuser");

        // 验证结果
        assertTrue(result);

        // 验证方法调用
        verify(userDomainService).findByUsername("testuser");
        verify(userDomainService).changePassword(eq(1L), anyString(), anyString());
    }

    @Test
    @DisplayName("测试修改密码失败 - 密码不匹配")
    void testChangePasswordFailurePasswordMismatch() {
        // 修改密码请求使密码不匹配
        passwordChangeRequest.setConfirmPassword("different");

        // 执行测试并验证异常
        assertThrows(BusinessException.class, () -> authService.changePassword(passwordChangeRequest, "testuser"));

        // 验证方法调用
        verify(userDomainService, never()).findByUsername(anyString());
        verify(userDomainService, never()).changePassword(anyLong(), anyString(), anyString());
    }

    @Test
    @DisplayName("测试修改密码失败 - 用户不存在")
    void testChangePasswordFailureUserNotFound() {
        // 准备模拟数据
        when(userDomainService.findByUsername("testuser")).thenReturn(Optional.empty());

        // 执行测试并验证异常
        assertThrows(EntityNotFoundException.class, () -> authService.changePassword(passwordChangeRequest, "testuser"));

        // 验证方法调用
        verify(userDomainService).findByUsername("testuser");
        verify(userDomainService, never()).changePassword(anyLong(), anyString(), anyString());
    }

    @Test
    @DisplayName("测试修改密码失败 - 旧密码不正确")
    void testChangePasswordFailureOldPasswordIncorrect() {
        // 准备模拟数据
        when(userDomainService.findByUsername("testuser")).thenReturn(Optional.of(testUser));
        when(userDomainService.changePassword(eq(1L), anyString(), anyString())).thenReturn(false);

        // 执行测试并验证异常
        assertThrows(BusinessException.class, () -> authService.changePassword(passwordChangeRequest, "testuser"));

        // 验证方法调用
        verify(userDomainService).findByUsername("testuser");
        verify(userDomainService).changePassword(eq(1L), anyString(), anyString());
    }

    @Test
    @DisplayName("测试刷新令牌成功")
    void testRefreshTokenSuccess() {
        // 准备模拟数据
        when(jwtTokenProvider.validateToken("refresh-token")).thenReturn(true);
        when(jwtTokenProvider.getUsername("refresh-token")).thenReturn("testuser");
        when(userDomainService.findByUsername("testuser")).thenReturn(Optional.of(testUser));
        when(userDomainService.getUserRoleIds(1L)).thenReturn(Collections.singletonList(1L));
        when(jwtTokenProvider.createToken(any(Authentication.class))).thenReturn("new-jwt-token");

        // 设置安全上下文
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // 执行测试
        AuthResponse response = authService.refreshToken("refresh-token");

        // 验证结果
        assertNotNull(response);
        assertEquals("new-jwt-token", response.getToken());
        assertEquals("Bearer", response.getTokenType());
        assertEquals(1L, response.getUserId());
        assertEquals("testuser", response.getUsername());
        assertEquals(1, response.getRoles().size());
        assertEquals("ROLE_1", response.getRoles().get(0));

        // 验证方法调用
        verify(jwtTokenProvider).validateToken("refresh-token");
        verify(jwtTokenProvider).getUsername("refresh-token");
        verify(userDomainService).findByUsername("testuser");
        verify(userDomainService).getUserRoleIds(1L);
        verify(jwtTokenProvider).createToken(any(Authentication.class));
    }

    @Test
    @DisplayName("测试刷新令牌失败 - 无效令牌")
    void testRefreshTokenFailureInvalidToken() {
        // 准备模拟数据
        when(jwtTokenProvider.validateToken("invalid-token")).thenReturn(false);

        // 执行测试并验证异常
        assertThrows(UnauthorizedException.class, () -> authService.refreshToken("invalid-token"));

        // 验证方法调用
        verify(jwtTokenProvider).validateToken("invalid-token");
        verify(jwtTokenProvider, never()).getUsername(anyString());
        verify(userDomainService, never()).findByUsername(anyString());
    }

    @Test
    @DisplayName("测试刷新令牌失败 - 用户不存在")
    void testRefreshTokenFailureUserNotFound() {
        // 准备模拟数据
        when(jwtTokenProvider.validateToken("refresh-token")).thenReturn(true);
        when(jwtTokenProvider.getUsername("refresh-token")).thenReturn("testuser");
        when(userDomainService.findByUsername("testuser")).thenReturn(Optional.empty());

        // 执行测试并验证异常
        assertThrows(EntityNotFoundException.class, () -> authService.refreshToken("refresh-token"));

        // 验证方法调用
        verify(jwtTokenProvider).validateToken("refresh-token");
        verify(jwtTokenProvider).getUsername("refresh-token");
        verify(userDomainService).findByUsername("testuser");
        verify(userDomainService, never()).getUserRoleIds(anyLong());
    }

    @Test
    @DisplayName("测试登出")
    void testLogout() {
        // 设置安全上下文
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // 执行测试
        authService.logout("testuser");

        // 验证安全上下文已清除
        assertNull(SecurityContextHolder.getContext().getAuthentication());
    }
}