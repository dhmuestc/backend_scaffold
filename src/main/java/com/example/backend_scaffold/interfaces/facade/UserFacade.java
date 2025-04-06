package com.example.backend_scaffold.interfaces.facade;

import com.example.backend_scaffold.application.dto.user.UserRequest;
import com.example.backend_scaffold.application.dto.user.UserResponse;
import com.example.backend_scaffold.application.dto.user.UserRoleRequest;
import com.example.backend_scaffold.application.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 用户外观类，作为应用服务的门面
 */
@Component
@RequiredArgsConstructor
public class UserFacade {

    private final UserService userService;

    /**
     * 创建用户
     *
     * @param request 用户请求
     * @return 用户响应
     */
    public UserResponse createUser(UserRequest request) {
        return userService.createUser(request);
    }

    /**
     * 更新用户
     *
     * @param id      用户ID
     * @param request 用户请求
     * @return 用户响应
     */
    public UserResponse updateUser(Long id, UserRequest request) {
        return userService.updateUser(id, request);
    }

    /**
     * 删除用户
     *
     * @param id 用户ID
     */
    public void deleteUser(Long id) {
        userService.deleteUser(id);
    }

    /**
     * 获取用户
     *
     * @param id 用户ID
     * @return 用户响应
     */
    public UserResponse getUser(Long id) {
        return userService.getUserById(id);
    }

    /**
     * 分页获取用户列表
     *
     * @param pageable 分页参数
     * @return 用户响应分页
     */
    public Page<UserResponse> getUsers(Pageable pageable) {
        return userService.getUsers(pageable);
    }

    /**
     * 获取所有用户
     *
     * @return 用户DTO列表
     */
    public List<UserResponse> getAllUsers() {
        return userService.getAllUsers();
    }

    /**
     * 分配角色给用户
     *
     * @param userId  用户ID
     * @param request 用户角色请求
     */
    public void assignRolesToUser(Long userId, UserRoleRequest request) {
        userService.assignRolesToUser(userId, request);
    }

    /**
     * 获取用户的角色
     *
     * @param userId 用户ID
     * @return 角色ID列表
     */
    public List<Long> getUserRoles(Long userId) {
        return userService.getUserRoles(userId);
    }
}