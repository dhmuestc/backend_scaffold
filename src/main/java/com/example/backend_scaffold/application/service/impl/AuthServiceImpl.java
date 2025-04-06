package com.example.backend_scaffold.application.service.impl;

import com.example.backend_scaffold.application.dto.auth.AuthRequest;
import com.example.backend_scaffold.application.dto.auth.AuthResponse;
import com.example.backend_scaffold.application.dto.auth.PasswordChangeRequest;
import com.example.backend_scaffold.application.dto.auth.RegisterRequest;
import com.example.backend_scaffold.application.dto.user.UserResponse;
import com.example.backend_scaffold.application.exception.BusinessException;
import com.example.backend_scaffold.application.exception.EntityNotFoundException;
import com.example.backend_scaffold.application.exception.UnauthorizedException;
import com.example.backend_scaffold.application.mapper.UserMapper;
import com.example.backend_scaffold.application.service.AuthService;
import com.example.backend_scaffold.domain.model.entity.UserEntity;
import com.example.backend_scaffold.domain.model.enums.UserStatus;
import com.example.backend_scaffold.domain.service.UserDomainService;
import com.example.backend_scaffold.infrastructure.security.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 认证服务实现类
 * <p>
 * 实现用户认证、注册、密码修改等功能
 * </p>
 *
 * @author example
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class AuthServiceImpl implements AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final UserDomainService userDomainService;
    private final UserMapper userMapper;

    @Override
    public AuthResponse login(AuthRequest authRequest) {
        log.info("User login attempt: {}", authRequest.getUsername());
        try {
            // 创建认证令牌
            UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                    authRequest.getUsername(),
                    authRequest.getPassword()
            );

            // 进行认证
            Authentication authentication = authenticationManager.authenticate(authToken);
            SecurityContextHolder.getContext().setAuthentication(authentication);

            // 获取用户角色
            List<String> roles = authentication.getAuthorities().stream()
                    .map(GrantedAuthority::getAuthority)
                    .collect(Collectors.toList());

            // 生成JWT令牌
            String token = jwtTokenProvider.createToken(authentication);

            // 获取用户信息
            UserEntity userEntity = userDomainService.findByUsername(authRequest.getUsername())
                    .orElseThrow(() -> new EntityNotFoundException("User not found with username: " + authRequest.getUsername()));

            log.info("User login successful: {}", authRequest.getUsername());

            // 构建并返回认证响应
            return AuthResponse.builder()
                    .token(token)
                    .tokenType("Bearer")
                    .userId(userEntity.getId())
                    .username(userEntity.getUsername())
                    .roles(roles)
                    .build();
        } catch (AuthenticationException e) {
            log.warn("Authentication failed for user: {}", authRequest.getUsername(), e);
            throw new UnauthorizedException("Invalid username or password");
        }
    }

    @Override
    @Transactional
    public UserResponse register(RegisterRequest registerRequest) {
        log.info("User registration attempt: {}", registerRequest.getUsername());

        // 验证密码确认
        if (!registerRequest.getPassword().equals(registerRequest.getConfirmPassword())) {
            throw new BusinessException("Password and confirm password do not match");
        }

        // 检查用户名是否已存在
        if (userDomainService.findByUsername(registerRequest.getUsername()).isPresent()) {
            throw new BusinessException("Username already exists: " + registerRequest.getUsername());
        }

        // 检查邮箱是否已存在
        if (userDomainService.findByEmail(registerRequest.getEmail()).isPresent()) {
            throw new BusinessException("Email already exists: " + registerRequest.getEmail());
        }

        // 创建用户实体
        UserEntity userEntity = new UserEntity();
        userEntity.setUsername(registerRequest.getUsername());
        userEntity.setPassword(registerRequest.getPassword()); // 领域服务会进行密码加密
        userEntity.setEmail(registerRequest.getEmail());
        userEntity.setStatus(UserStatus.ACTIVE);

        // 保存用户
        UserEntity savedUser = userDomainService.createUser(userEntity);
        log.info("User registration successful: {}", registerRequest.getUsername());

        // 转换并返回用户响应
        return userMapper.toResponse(userMapper.toDto(savedUser));
    }

    @Override
    @Transactional
    public boolean changePassword(PasswordChangeRequest passwordChangeRequest, String username) {
        log.info("Password change attempt for user: {}", username);

        // 验证密码确认
        if (!passwordChangeRequest.getNewPassword().equals(passwordChangeRequest.getConfirmPassword())) {
            throw new BusinessException("New password and confirm password do not match");
        }

        // 获取用户
        UserEntity userEntity = userDomainService.findByUsername(username)
                .orElseThrow(() -> new EntityNotFoundException("User not found with username: " + username));

        // 修改密码
        boolean result = userDomainService.changePassword(
                userEntity.getId(),
                passwordChangeRequest.getOldPassword(),
                passwordChangeRequest.getNewPassword()
        );

        if (!result) {
            throw new BusinessException("Old password is incorrect");
        }

        log.info("Password change successful for user: {}", username);
        return true;
    }

    @Override
    public AuthResponse refreshToken(String refreshToken) {
        // 这里简化实现，实际应该使用专门的刷新令牌
        log.info("Token refresh attempt");

        // 验证令牌
        if (!jwtTokenProvider.validateToken(refreshToken)) {
            throw new UnauthorizedException("Invalid refresh token");
        }

        // 获取用户名
        String username = jwtTokenProvider.getUsername(refreshToken);

        // 获取用户
        UserEntity userEntity = userDomainService.findByUsername(username)
                .orElseThrow(() -> new EntityNotFoundException("User not found with username: " + username));

        // 获取用户角色
        List<Long> roleIds = userDomainService.getUserRoleIds(userEntity.getId());
        List<String> roles = roleIds.stream().map(id -> "ROLE_" + id).collect(Collectors.toList());

        // 创建新的认证对象
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // 生成新的JWT令牌
        String newToken = jwtTokenProvider.createToken(authentication);

        log.info("Token refresh successful for user: {}", username);

        // 构建并返回认证响应
        return AuthResponse.builder()
                .token(newToken)
                .tokenType("Bearer")
                .userId(userEntity.getId())
                .username(userEntity.getUsername())
                .roles(roles)
                .build();
    }

    @Override
    public void logout(String username) {
        log.info("User logout: {}", username);
        // 清除安全上下文
        SecurityContextHolder.clearContext();
        
        // 实际项目中，可能还需要将令牌加入黑名单或使用Redis缓存来使令牌失效
        // 例如：cacheService.invalidateToken(username);
    }
}