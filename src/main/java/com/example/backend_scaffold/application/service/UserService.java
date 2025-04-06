package com.example.backend_scaffold.application.service;

import com.example.backend_scaffold.application.dto.user.UserRequest;
import com.example.backend_scaffold.application.dto.user.UserResponse;
import com.example.backend_scaffold.application.dto.user.UserRoleRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * 用户服务接口
 * <p>
 * 提供用户的创建、查询、更新、删除等功能
 * </p>
 *
 * @author example
 */
public interface UserService {

    /**
     * 创建用户
     *
     * @param userRequest 用户请求DTO
     * @return 用户响应DTO
     */
    UserResponse createUser(UserRequest userRequest);

    /**
     * 更新用户
     *
     * @param id          用户ID
     * @param userRequest 用户请求DTO
     * @return 用户响应DTO
     */
    UserResponse updateUser(Long id, UserRequest userRequest);

    /**
     * 根据ID查询用户
     *
     * @param id 用户ID
     * @return 用户响应DTO
     */
    UserResponse getUserById(Long id);

    /**
     * 根据用户名查询用户
     *
     * @param username 用户名
     * @return 用户响应DTO
     */
    UserResponse getUserByUsername(String username);

    /**
     * 根据邮箱查询用户
     *
     * @param email 邮箱
     * @return 用户响应DTO
     */
    UserResponse getUserByEmail(String email);

    /**
     * 查询所有用户
     *
     * @return 用户响应DTO列表
     */
    List<UserResponse> getAllUsers();

    /**
     * 删除用户
     *
     * @param id 用户ID
     */
    void deleteUser(Long id);

    /**
     * 为用户分配角色
     *
     * @param userId         用户ID
     * @param userRoleRequest 用户角色请求DTO
     * @return 用户响应DTO
     */
    UserResponse assignRolesToUser(Long userId, UserRoleRequest userRoleRequest);

    /**
     * 获取用户的所有角色
     *
     * @param userId 用户ID
     * @return 用户响应DTO，包含角色信息
     */
    List<Long> getUserRoles(Long userId);

    /**
     * 修改用户状态
     *
     * @param id     用户ID
     * @param status 状态值
     * @return 用户响应DTO
     */
    UserResponse updateUserStatus(Long id, Integer status);


    /**
     * 分页获取用户列表
     * 
     * @param pageable
     * @return 用户响应分页
     */
    Page<UserResponse> getUsers(Pageable pageable);
}