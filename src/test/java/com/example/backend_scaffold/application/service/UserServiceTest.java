package com.example.backend_scaffold.application.service;

import com.example.backend_scaffold.application.dto.user.UserRequest;
import com.example.backend_scaffold.application.dto.user.UserResponse;
import com.example.backend_scaffold.application.dto.user.UserRoleRequest;
import com.example.backend_scaffold.application.exception.EntityNotFoundException;
import com.example.backend_scaffold.application.mapper.UserMapper;
import com.example.backend_scaffold.application.service.impl.UserServiceImpl;
import com.example.backend_scaffold.domain.model.entity.UserEntity;
import com.example.backend_scaffold.domain.model.enums.UserStatus;
import com.example.backend_scaffold.domain.service.UserDomainService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserDomainService userDomainService;

    @Mock
    private UserMapper userMapper;

    @InjectMocks
    private UserServiceImpl userService;

    private UserEntity testUser;
    private UserRequest userRequest;
    private UserResponse userResponse;
    private List<Long> roleIds;

    @BeforeEach
    void setUp() {
        // 创建测试用户实体
        testUser = new UserEntity();
        testUser.setId(1L);
        testUser.setUsername("testuser");
        testUser.setPassword("password");
        testUser.setEmail("test@example.com");
        testUser.setStatus(UserStatus.ACTIVE);

        // 创建用户请求
        userRequest = new UserRequest();
        userRequest.setUsername("testuser");
        userRequest.setEmail("test@example.com");

        // 创建用户响应
        userResponse = new UserResponse();
        userResponse.setId(1L);
        userResponse.setUsername("testuser");
        userResponse.setEmail("test@example.com");
        userResponse.setStatus(UserStatus.ACTIVE);

        // 创建角色ID列表
        roleIds = Collections.singletonList(1L);
    }

    @Test
    @DisplayName("测试创建用户成功")
    void testCreateUserSuccess() {
        // 准备模拟数据
        when(userMapper.toEntity(any(UserRequest.class))).thenReturn(testUser);
        when(userDomainService.createUser(any(UserEntity.class))).thenReturn(testUser);
        when(userMapper.entityToResponse(any(UserEntity.class))).thenReturn(userResponse);

        // 执行测试
        UserResponse response = userService.createUser(userRequest);

        // 验证结果
        assertNotNull(response);
        assertEquals(1L, response.getId());
        assertEquals("testuser", response.getUsername());
        assertEquals("test@example.com", response.getEmail());

        // 验证方法调用
        verify(userMapper).toEntity(any(UserRequest.class));
        verify(userDomainService).createUser(any(UserEntity.class));
        verify(userMapper).entityToResponse(any(UserEntity.class));
    }

    @Test
    @DisplayName("测试更新用户成功")
    void testUpdateUserSuccess() {
        // 准备模拟数据
        when(userDomainService.findById(1L)).thenReturn(Optional.of(testUser));
        when(userMapper.updateEntity(any(UserRequest.class), any(UserEntity.class))).thenReturn(testUser);
        when(userDomainService.updateUser(any(UserEntity.class))).thenReturn(testUser);
        when(userMapper.entityToResponse(any(UserEntity.class))).thenReturn(userResponse);

        // 执行测试
        UserResponse response = userService.updateUser(1L, userRequest);

        // 验证结果
        assertNotNull(response);
        assertEquals(1L, response.getId());
        assertEquals("testuser", response.getUsername());
        assertEquals("test@example.com", response.getEmail());

        // 验证方法调用
        verify(userDomainService).findById(1L);
        verify(userMapper).updateEntity(any(UserRequest.class), any(UserEntity.class));
        verify(userDomainService).updateUser(any(UserEntity.class));
        verify(userMapper).entityToResponse(any(UserEntity.class));
    }

    @Test
    @DisplayName("测试更新用户失败 - 用户不存在")
    void testUpdateUserFailureUserNotFound() {
        // 准备模拟数据
        when(userDomainService.findById(1L)).thenReturn(Optional.empty());

        // 执行测试并验证异常
        assertThrows(EntityNotFoundException.class, () -> userService.updateUser(1L, userRequest));

        // 验证方法调用
        verify(userDomainService).findById(1L);
        verify(userMapper, never()).updateEntity(any(UserRequest.class), any(UserEntity.class));
        verify(userDomainService, never()).updateUser(any(UserEntity.class));
    }

    @Test
    @DisplayName("测试根据ID查询用户成功")
    void testGetUserByIdSuccess() {
        // 准备模拟数据
        when(userDomainService.findById(1L)).thenReturn(Optional.of(testUser));
        when(userMapper.entityToResponse(any(UserEntity.class))).thenReturn(userResponse);
        when(userDomainService.getUserRoleIds(1L)).thenReturn(roleIds);

        // 执行测试
        UserResponse response = userService.getUserById(1L);

        // 验证结果
        assertNotNull(response);
        assertEquals(1L, response.getId());
        assertEquals("testuser", response.getUsername());
        assertEquals("test@example.com", response.getEmail());
        assertEquals(roleIds, response.getRoleIds());

        // 验证方法调用
        verify(userDomainService).findById(1L);
        verify(userMapper).entityToResponse(any(UserEntity.class));
        verify(userDomainService).getUserRoleIds(1L);
    }

    @Test
    @DisplayName("测试根据ID查询用户失败 - 用户不存在")
    void testGetUserByIdFailureUserNotFound() {
        // 准备模拟数据
        when(userDomainService.findById(1L)).thenReturn(Optional.empty());

        // 执行测试并验证异常
        assertThrows(EntityNotFoundException.class, () -> userService.getUserById(1L));

        // 验证方法调用
        verify(userDomainService).findById(1L);
        verify(userMapper, never()).entityToResponse(any(UserEntity.class));
        verify(userDomainService, never()).getUserRoleIds(anyLong());
    }

    @Test
    @DisplayName("测试根据用户名查询用户成功")
    void testGetUserByUsernameSuccess() {
        // 准备模拟数据
        when(userDomainService.findByUsername("testuser")).thenReturn(Optional.of(testUser));
        when(userMapper.entityToResponse(any(UserEntity.class))).thenReturn(userResponse);
        when(userDomainService.getUserRoleIds(1L)).thenReturn(roleIds);

        // 执行测试
        UserResponse response = userService.getUserByUsername("testuser");

        // 验证结果
        assertNotNull(response);
        assertEquals(1L, response.getId());
        assertEquals("testuser", response.getUsername());
        assertEquals("test@example.com", response.getEmail());
        assertEquals(roleIds, response.getRoleIds());

        // 验证方法调用
        verify(userDomainService).findByUsername("testuser");
        verify(userMapper).entityToResponse(any(UserEntity.class));
        verify(userDomainService).getUserRoleIds(1L);
    }

    @Test
    @DisplayName("测试根据用户名查询用户失败 - 用户不存在")
    void testGetUserByUsernameFailureUserNotFound() {
        // 准备模拟数据
        when(userDomainService.findByUsername("testuser")).thenReturn(Optional.empty());

        // 执行测试并验证异常
        assertThrows(EntityNotFoundException.class, () -> userService.getUserByUsername("testuser"));

        // 验证方法调用
        verify(userDomainService).findByUsername("testuser");
        verify(userMapper, never()).entityToResponse(any(UserEntity.class));
        verify(userDomainService, never()).getUserRoleIds(anyLong());
    }

    @Test
    @DisplayName("测试根据邮箱查询用户成功")
    void testGetUserByEmailSuccess() {
        // 准备模拟数据
        when(userDomainService.findByEmail("test@example.com")).thenReturn(Optional.of(testUser));
        when(userMapper.entityToResponse(any(UserEntity.class))).thenReturn(userResponse);
        when(userDomainService.getUserRoleIds(1L)).thenReturn(roleIds);

        // 执行测试
        UserResponse response = userService.getUserByEmail("test@example.com");

        // 验证结果
        assertNotNull(response);
        assertEquals(1L, response.getId());
        assertEquals("testuser", response.getUsername());
        assertEquals("test@example.com", response.getEmail());
        assertEquals(roleIds, response.getRoleIds());

        // 验证方法调用
        verify(userDomainService).findByEmail("test@example.com");
        verify(userMapper).entityToResponse(any(UserEntity.class));
        verify(userDomainService).getUserRoleIds(1L);
    }

    @Test
    @DisplayName("测试分页查询所有用户成功")
    void testGetAllUsersSuccess() {
        // 准备模拟数据
        Pageable pageable = PageRequest.of(0, 10);
        List<UserEntity> userEntities = Collections.singletonList(testUser);
        Page<UserEntity> userEntityPage = new PageImpl<>(userEntities, pageable, 1);
        
        when(userDomainService.findAll(pageable)).thenReturn(userEntityPage);
        when(userMapper.entityToResponse(any(UserEntity.class))).thenReturn(userResponse);
        when(userDomainService.getUserRoleIds(1L)).thenReturn(roleIds);

        // 执行测试
        Page<UserResponse> responsePage = userService.getUsers(pageable);

        // 验证结果
        assertNotNull(responsePage);
        assertEquals(1, responsePage.getTotalElements());
        assertEquals(1, responsePage.getContent().size());
        assertEquals(1L, responsePage.getContent().get(0).getId());
        assertEquals("testuser", responsePage.getContent().get(0).getUsername());

        // 验证方法调用
        verify(userDomainService).findAll(pageable);
        verify(userMapper).entityToResponse(any(UserEntity.class));
        verify(userDomainService).getUserRoleIds(1L);
    }

    @Test
    @DisplayName("测试删除用户成功")
    void testDeleteUserSuccess() {
        // 准备模拟数据
        when(userDomainService.findById(1L)).thenReturn(Optional.of(testUser));
        doNothing().when(userDomainService).deleteUser(1L);

        // 执行测试
        userService.deleteUser(1L);

        // 验证方法调用
        verify(userDomainService).findById(1L);
        verify(userDomainService).deleteUser(1L);
    }

    @Test
    @DisplayName("测试删除用户失败 - 用户不存在")
    void testDeleteUserFailureUserNotFound() {
        // 准备模拟数据
        when(userDomainService.findById(1L)).thenReturn(Optional.empty());

        // 执行测试并验证异常
        assertThrows(EntityNotFoundException.class, () -> userService.deleteUser(1L));

        // 验证方法调用
        verify(userDomainService).findById(1L);
        verify(userDomainService, never()).deleteUser(anyLong());
    }

    @Test
    @DisplayName("测试分配用户角色成功")
    void testAssignRolesToUserSuccess() {
        // 准备模拟数据
        UserRoleRequest userRoleRequest = new UserRoleRequest();
        userRoleRequest.setRoleIds(Arrays.asList(1L, 2L));
        
        when(userDomainService.findById(1L)).thenReturn(Optional.of(testUser));
        when(userMapper.entityToResponse(any(UserEntity.class))).thenReturn(userResponse);
        when(userDomainService.getUserRoleIds(1L)).thenReturn(Arrays.asList(1L, 2L));

        // 执行测试
        UserResponse result = userService.assignRolesToUser(1L, userRoleRequest);

        // 验证结果
        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("testuser", result.getUsername());

        // 验证方法调用
        verify(userDomainService).findById(1L);
        verify(userMapper).entityToResponse(any(UserEntity.class));
        verify(userDomainService).getUserRoleIds(1L);
    }

    @Test
    @DisplayName("测试分配用户角色失败 - 用户不存在")
    void testAssignRolesToUserFailureUserNotFound() {
        // 准备模拟数据
        UserRoleRequest userRoleRequest = new UserRoleRequest();
        userRoleRequest.setRoleIds(Arrays.asList(1L, 2L));
        
        when(userDomainService.findById(1L)).thenReturn(Optional.empty());

        // 执行测试并验证异常
        assertThrows(EntityNotFoundException.class, () -> userService.assignRolesToUser(1L, userRoleRequest));

        // 验证方法调用
        verify(userDomainService).findById(1L);
        verify(userMapper, never()).entityToResponse(any(UserEntity.class));
    }

    // 移除用户角色的测试方法已删除，因为UserService接口中没有定义removeRolesFromUser方法
}