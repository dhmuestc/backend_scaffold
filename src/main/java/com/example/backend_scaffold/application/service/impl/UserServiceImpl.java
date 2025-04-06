package com.example.backend_scaffold.application.service.impl;

import com.example.backend_scaffold.application.dto.user.UserRequest;
import com.example.backend_scaffold.application.dto.user.UserResponse;
import com.example.backend_scaffold.application.dto.user.UserRoleRequest;
import com.example.backend_scaffold.application.exception.EntityNotFoundException;
import com.example.backend_scaffold.application.mapper.UserMapper;
import com.example.backend_scaffold.application.service.UserService;
import com.example.backend_scaffold.domain.model.entity.UserEntity;
import com.example.backend_scaffold.domain.service.UserDomainService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 用户服务实现类
 * <p>
 * 实现用户的创建、查询、更新、删除等功能
 * </p>
 *
 * @author example
 */
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserDomainService userDomainService;
    private final UserMapper userMapper;

    @Override
    @Transactional
    public UserResponse createUser(UserRequest userRequest) {
        // 将请求DTO转换为实体
        UserEntity userEntity = userMapper.toEntity(userRequest);
        
        // 调用领域服务创建用户
        UserEntity createdUser = userDomainService.createUser(userEntity);
        
        // 直接将实体转换为响应DTO
        return userMapper.entityToResponse(createdUser);
    }

    @Override
    @Transactional
    public UserResponse updateUser(Long id, UserRequest userRequest) {
        // 查询用户是否存在
        UserEntity existingUser = userDomainService.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + id));
        
        // 更新用户实体
        UserEntity updatedEntity = userMapper.updateEntity(userRequest, existingUser);
        
        // 调用领域服务更新用户
        UserEntity savedUser = userDomainService.updateUser(updatedEntity);
        
        // 直接将实体转换为响应DTO
        return userMapper.entityToResponse(savedUser);
    }

    @Override
    public UserResponse getUserById(Long id) {
        // 查询用户是否存在
        UserEntity userEntity = userDomainService.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + id));
        
        // 将实体转换为DTO
        UserResponse userResponse = userMapper.entityToResponse(userEntity);
        
        // 获取用户角色
        List<Long> roleIds = userDomainService.getUserRoleIds(id);
        userResponse.setRoleIds(roleIds);
        
        return userResponse;
    }

    @Override
    public UserResponse getUserByUsername(String username) {
        // 查询用户是否存在
        UserEntity userEntity = userDomainService.findByUsername(username)
                .orElseThrow(() -> new EntityNotFoundException("User not found with username: " + username));
        
        // 将实体转换为DTO
        UserResponse userResponse = userMapper.entityToResponse(userEntity);

        // 获取用户角色
        List<Long> roleIds = userDomainService.getUserRoleIds(userEntity.getId());
        userResponse.setRoleIds(roleIds);
        
        return userResponse;
    }

    @Override
    public UserResponse getUserByEmail(String email) {
        // 查询用户是否存在
        UserEntity userEntity = userDomainService.findByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException("User not found with email: " + email));
        
        // 将实体转换为DTO
        UserResponse userResponse = userMapper.entityToResponse(userEntity);

        // 获取用户角色
        List<Long> roleIds = userDomainService.getUserRoleIds(userEntity.getId());
        userResponse.setRoleIds(roleIds);
        
        return userResponse;
    }

    @Override
    public List<UserResponse> getAllUsers() {
        // 获取所有用户
        List<UserEntity> userEntities = userDomainService.findAll();
        
        // 将实体列表转换为DTO列表
        List<UserResponse> userResponses = userMapper.entitiesToResponseList(userEntities);
        
        // 为每个用户设置角色
        userResponses.forEach(userRespons -> {
            List<Long> roleIds = userDomainService.getUserRoleIds(userRespons.getId());
            userRespons.setRoleIds(roleIds);
        });
        
        return userResponses;
    }

    @Override
    @Transactional
    public void deleteUser(Long id) {
        // 查询用户是否存在
        if (!userDomainService.findById(id).isPresent()) {
            throw new EntityNotFoundException("User not found with id: " + id);
        }
        
        // 调用领域服务删除用户
        userDomainService.deleteUser(id);
    }

    @Override
    @Transactional
    public UserResponse assignRolesToUser(Long userId, UserRoleRequest userRoleRequest) {
        // 查询用户是否存在
        userDomainService.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + userId));
        
        // 先移除用户的所有角色
        List<Long> existingRoleIds = userDomainService.getUserRoleIds(userId);
        existingRoleIds.forEach(roleId -> userDomainService.removeRole(userId, roleId));
        
        // 分配新角色
        userRoleRequest.getRoleIds().forEach(roleId -> userDomainService.assignRole(userId, roleId));
        
        // 获取更新后的用户信息
        // 重新获取用户实体，确保获取最新状态
        UserEntity updatedUserEntity = userDomainService.findById(userId).get();
        UserResponse userResponse = userMapper.entityToResponse(updatedUserEntity);
        
        // 设置最新的角色ID列表
        List<Long> updatedRoleIds = userDomainService.getUserRoleIds(userId);
        userResponse.setRoleIds(updatedRoleIds);
        
        return userResponse;
    }

    @Override
    public List<Long> getUserRoles(Long userId) {
        // 查询用户是否存在
        if (!userDomainService.findById(userId).isPresent()) {
            throw new EntityNotFoundException("User not found with id: " + userId);
        }
        
        // 获取用户角色
        return userDomainService.getUserRoleIds(userId);
    }

    @Override
    @Transactional
    public UserResponse updateUserStatus(Long id, Integer status) {
        // 查询用户是否存在
        userDomainService.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + id));
        
        // 根据状态值更新用户状态
        boolean success = false;
        switch (status) {
            case 0: // 禁用
                success = userDomainService.disableUser(id);
                break;
            case 1: // 启用
                success = userDomainService.enableUser(id);
                break;
            case 2: // 锁定
                success = userDomainService.lockUser(id);
                break;
            case 3: // 解锁
                success = userDomainService.unlockUser(id);
                break;
            default:
                throw new IllegalArgumentException("Invalid status value: " + status);
        }
        
        if (!success) {
            throw new RuntimeException("Failed to update user status");
        }
        
        // 重新获取用户信息
        UserEntity updatedUser = userDomainService.findById(id).get();
        UserResponse userResponse = userMapper.entityToResponse(updatedUser);
        
        // 获取用户角色
        List<Long> roleIds = userDomainService.getUserRoleIds(id);
        userResponse.setRoleIds(roleIds);
        
        return userResponse;
    }

    @Override
    public Page<UserResponse> getUsers(Pageable pageable) {
        // 获取所有用户
        List<UserEntity> userEntities = userDomainService.findAll();
        
        // 手动分页处理
        int start = (int) pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), userEntities.size());
        
        // 获取当前页的用户列表
        List<UserEntity> pageContent = userEntities.subList(start, end);
        
        // 将实体列表转换为DTO列表
        List<UserResponse> userResponses = userMapper.entitiesToResponseList(pageContent);
        
        // 为每个用户设置角色
        userResponses.forEach(userResponse -> {
            List<Long> roleIds = userDomainService.getUserRoleIds(userResponse.getId());
            userResponse.setRoleIds(roleIds);
        });
        
        // 创建分页响应
        return new PageImpl<>(userResponses, pageable, userEntities.size());
    }
}