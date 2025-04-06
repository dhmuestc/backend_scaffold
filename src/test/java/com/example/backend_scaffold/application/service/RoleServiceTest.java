package com.example.backend_scaffold.application.service;

import com.example.backend_scaffold.application.dto.role.RolePermissionRequest;
import com.example.backend_scaffold.application.dto.role.RoleRequest;
import com.example.backend_scaffold.application.dto.role.RoleResponse;
import com.example.backend_scaffold.application.exception.BusinessException;
import com.example.backend_scaffold.application.exception.EntityNotFoundException;
import com.example.backend_scaffold.application.mapper.RoleMapper;
import com.example.backend_scaffold.application.service.impl.RoleServiceImpl;
import com.example.backend_scaffold.domain.model.entity.RoleEntity;
import com.example.backend_scaffold.domain.repository.RoleRepository;
import com.example.backend_scaffold.domain.service.RoleDomainService;
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
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class RoleServiceTest {

    @Mock
    private RoleDomainService roleDomainService;

    @Mock
    private RoleMapper roleMapper;

    @Mock
    private RoleRepository roleRepository;

    @InjectMocks
    private RoleServiceImpl roleService;

    private RoleEntity testRole;
    private RoleRequest roleRequest;
    private RoleResponse roleResponse;
    private List<Long> permissionIds;

    @BeforeEach
    void setUp() {
        // 创建测试角色实体
        testRole = new RoleEntity();
        testRole.setId(1L);
        testRole.setName("测试角色");
        testRole.setCode("TEST_ROLE");
        testRole.setDescription("这是一个测试角色");

        // 创建角色请求
        roleRequest = new RoleRequest();
        roleRequest.setName("测试角色");
        roleRequest.setCode("TEST_ROLE");
        roleRequest.setDescription("这是一个测试角色");

        // 创建角色响应
        roleResponse = new RoleResponse();
        roleResponse.setId(1L);
        roleResponse.setName("测试角色");
        roleResponse.setCode("TEST_ROLE");
        roleResponse.setDescription("这是一个测试角色");

        // 创建权限ID列表
        permissionIds = Arrays.asList(1L, 2L, 3L);
    }

    @Test
    @DisplayName("测试创建角色成功")
    void testCreateRoleSuccess() {
        // 准备模拟数据
        when(roleMapper.toEntity(any(RoleRequest.class))).thenReturn(testRole);
        when(roleDomainService.createRole(any(RoleEntity.class))).thenReturn(testRole);
        when(roleMapper.entityToResponse(any(RoleEntity.class))).thenReturn(roleResponse);

        // 执行测试
        RoleResponse response = roleService.createRole(roleRequest);

        // 验证结果
        assertNotNull(response);
        assertEquals(1L, response.getId());
        assertEquals("测试角色", response.getName());
        assertEquals("TEST_ROLE", response.getCode());

        // 验证方法调用
        verify(roleMapper).toEntity(any(RoleRequest.class));
        verify(roleDomainService).createRole(any(RoleEntity.class));
        verify(roleMapper).entityToResponse(any(RoleEntity.class));
    }

    @Test
    @DisplayName("测试创建角色失败 - 业务异常")
    void testCreateRoleFailureBusinessException() {
        // 准备模拟数据
        when(roleMapper.toEntity(any(RoleRequest.class))).thenReturn(testRole);
        when(roleDomainService.createRole(any(RoleEntity.class)))
                .thenThrow(new RuntimeException("创建角色失败"));

        // 执行测试并验证异常
        assertThrows(BusinessException.class, () -> roleService.createRole(roleRequest));

        // 验证方法调用
        verify(roleMapper).toEntity(any(RoleRequest.class));
        verify(roleDomainService).createRole(any(RoleEntity.class));
        verify(roleMapper, never()).entityToResponse(any(RoleEntity.class));
    }

    @Test
    @DisplayName("测试更新角色成功")
    void testUpdateRoleSuccess() {
        // 准备模拟数据
        when(roleDomainService.findById(1L)).thenReturn(Optional.of(testRole));
        when(roleMapper.updateEntity(any(RoleRequest.class), any(RoleEntity.class))).thenReturn(testRole);
        when(roleDomainService.updateRole(any(RoleEntity.class))).thenReturn(testRole);
        when(roleMapper.entityToResponse(any(RoleEntity.class))).thenReturn(roleResponse);

        // 执行测试
        RoleResponse response = roleService.updateRole(1L, roleRequest);

        // 验证结果
        assertNotNull(response);
        assertEquals(1L, response.getId());
        assertEquals("测试角色", response.getName());
        assertEquals("TEST_ROLE", response.getCode());

        // 验证方法调用
        verify(roleDomainService).findById(1L);
        verify(roleMapper).updateEntity(any(RoleRequest.class), any(RoleEntity.class));
        verify(roleDomainService).updateRole(any(RoleEntity.class));
        verify(roleMapper).entityToResponse(any(RoleEntity.class));
    }

    @Test
    @DisplayName("测试更新角色失败 - 角色不存在")
    void testUpdateRoleFailureRoleNotFound() {
        // 准备模拟数据
        when(roleDomainService.findById(1L)).thenReturn(Optional.empty());

        // 执行测试并验证异常
        assertThrows(EntityNotFoundException.class, () -> roleService.updateRole(1L, roleRequest));

        // 验证方法调用
        verify(roleDomainService).findById(1L);
        verify(roleMapper, never()).updateEntity(any(RoleRequest.class), any(RoleEntity.class));
        verify(roleDomainService, never()).updateRole(any(RoleEntity.class));
    }

    @Test
    @DisplayName("测试更新角色失败 - 业务异常")
    void testUpdateRoleFailureBusinessException() {
        // 准备模拟数据
        when(roleDomainService.findById(1L)).thenReturn(Optional.of(testRole));
        when(roleMapper.updateEntity(any(RoleRequest.class), any(RoleEntity.class))).thenReturn(testRole);
        when(roleDomainService.updateRole(any(RoleEntity.class)))
                .thenThrow(new RuntimeException("更新角色失败"));

        // 执行测试并验证异常
        assertThrows(BusinessException.class, () -> roleService.updateRole(1L, roleRequest));

        // 验证方法调用
        verify(roleDomainService).findById(1L);
        verify(roleMapper).updateEntity(any(RoleRequest.class), any(RoleEntity.class));
        verify(roleDomainService).updateRole(any(RoleEntity.class));
        verify(roleMapper, never()).entityToResponse(any(RoleEntity.class));
    }

    @Test
    @DisplayName("测试根据ID查询角色成功")
    void testGetRoleByIdSuccess() {
        // 准备模拟数据
        when(roleDomainService.findById(1L)).thenReturn(Optional.of(testRole));
        when(roleMapper.entityToResponse(any(RoleEntity.class))).thenReturn(roleResponse);

        // 执行测试
        RoleResponse response = roleService.getRoleById(1L);

        // 验证结果
        assertNotNull(response);
        assertEquals(1L, response.getId());
        assertEquals("测试角色", response.getName());
        assertEquals("TEST_ROLE", response.getCode());

        // 验证方法调用
        verify(roleDomainService).findById(1L);
        verify(roleMapper).entityToResponse(any(RoleEntity.class));
    }

    @Test
    @DisplayName("测试根据ID查询角色失败 - 角色不存在")
    void testGetRoleByIdFailureRoleNotFound() {
        // 准备模拟数据
        when(roleDomainService.findById(1L)).thenReturn(Optional.empty());

        // 执行测试并验证异常
        assertThrows(EntityNotFoundException.class, () -> roleService.getRoleById(1L));

        // 验证方法调用
        verify(roleDomainService).findById(1L);
        verify(roleMapper, never()).entityToResponse(any(RoleEntity.class));
    }

    @Test
    @DisplayName("测试根据编码查询角色成功")
    void testGetRoleByCodeSuccess() {
        // 准备模拟数据
        when(roleDomainService.findByCode("TEST_ROLE")).thenReturn(Optional.of(testRole));
        when(roleMapper.entityToResponse(any(RoleEntity.class))).thenReturn(roleResponse);

        // 执行测试
        RoleResponse response = roleService.getRoleByCode("TEST_ROLE");

        // 验证结果
        assertNotNull(response);
        assertEquals(1L, response.getId());
        assertEquals("测试角色", response.getName());
        assertEquals("TEST_ROLE", response.getCode());

        // 验证方法调用
        verify(roleDomainService).findByCode("TEST_ROLE");
        verify(roleMapper).entityToResponse(any(RoleEntity.class));
    }

    @Test
    @DisplayName("测试根据编码查询角色失败 - 角色不存在")
    void testGetRoleByCodeFailureRoleNotFound() {
        // 准备模拟数据
        when(roleDomainService.findByCode("TEST_ROLE")).thenReturn(Optional.empty());

        // 执行测试并验证异常
        assertThrows(EntityNotFoundException.class, () -> roleService.getRoleByCode("TEST_ROLE"));

        // 验证方法调用
        verify(roleDomainService).findByCode("TEST_ROLE");
        verify(roleMapper, never()).entityToResponse(any(RoleEntity.class));
    }

    @Test
    @DisplayName("测试查询所有角色成功")
    void testGetAllRolesSuccess() {
        // 准备模拟数据
        List<RoleEntity> roleEntities = Collections.singletonList(testRole);
        List<RoleResponse> roleResponses = Collections.singletonList(roleResponse);
        
        when(roleDomainService.findAll()).thenReturn(roleEntities);
        when(roleMapper.entitesToResponseList(roleEntities)).thenReturn(roleResponses);

        // 执行测试
        List<RoleResponse> responses = roleService.getAllRoles();

        // 验证结果
        assertNotNull(responses);
        assertEquals(1, responses.size());
        assertEquals(1L, responses.get(0).getId());
        assertEquals("测试角色", responses.get(0).getName());
        assertEquals("TEST_ROLE", responses.get(0).getCode());

        // 验证方法调用
        verify(roleDomainService).findAll();
        verify(roleMapper).entitesToResponseList(roleEntities);
    }

    @Test
    @DisplayName("测试删除角色成功")
    void testDeleteRoleSuccess() {
        // 准备模拟数据
        doNothing().when(roleDomainService).deleteRole(1L);

        // 执行测试
        roleService.deleteRole(1L);

        // 验证方法调用
        verify(roleDomainService).deleteRole(1L);
    }

    @Test
    @DisplayName("测试删除角色失败 - 业务异常")
    void testDeleteRoleFailureBusinessException() {
        // 准备模拟数据
        doThrow(new RuntimeException("删除角色失败")).when(roleDomainService).deleteRole(1L);

        // 执行测试并验证异常
        assertThrows(BusinessException.class, () -> roleService.deleteRole(1L));

        // 验证方法调用
        verify(roleDomainService).deleteRole(1L);
    }

    @Test
    @DisplayName("测试分配权限给角色成功")
    void testAssignPermissionsToRoleSuccess() {
        // 准备模拟数据
        RolePermissionRequest request = new RolePermissionRequest();
        request.setPermissionIds(permissionIds);
        
        when(roleDomainService.findById(1L)).thenReturn(Optional.of(testRole));
        when(roleDomainService.getRolePermissionIds(1L)).thenReturn(Arrays.asList(4L, 5L));
        doNothing().when(roleDomainService).removePermission(anyLong(), anyLong());
        doNothing().when(roleDomainService).assignPermission(anyLong(), anyLong());
        when(roleMapper.entityToResponse(testRole)).thenReturn(roleResponse);

        // 执行测试
        RoleResponse response = roleService.assignPermissionsToRole(1L, request);

        // 验证结果
        assertNotNull(response);
        assertEquals(permissionIds, response.getPermissionIds());

        // 验证方法调用
        verify(roleDomainService).findById(1L);
        verify(roleDomainService).getRolePermissionIds(1L);
        verify(roleDomainService, times(2)).removePermission(eq(1L), anyLong());
        verify(roleDomainService, times(3)).assignPermission(eq(1L), anyLong());
        verify(roleMapper).entityToResponse(testRole);
    }

    @Test
    @DisplayName("测试分配权限给角色失败 - 角色不存在")
    void testAssignPermissionsToRoleFailureRoleNotFound() {
        // 准备模拟数据
        RolePermissionRequest request = new RolePermissionRequest();
        request.setPermissionIds(permissionIds);
        
        when(roleDomainService.findById(1L)).thenReturn(Optional.empty());

        // 执行测试并验证异常
        assertThrows(EntityNotFoundException.class, () -> roleService.assignPermissionsToRole(1L, request));

        // 验证方法调用
        verify(roleDomainService).findById(1L);
        verify(roleDomainService, never()).getRolePermissionIds(anyLong());
        verify(roleDomainService, never()).removePermission(anyLong(), anyLong());
        verify(roleDomainService, never()).assignPermission(anyLong(), anyLong());
    }

    @Test
    @DisplayName("测试获取角色权限成功")
    void testGetRolePermissionsSuccess() {
        // 准备模拟数据
        when(roleDomainService.findById(1L)).thenReturn(Optional.of(testRole));
        when(roleDomainService.getRolePermissionIds(1L)).thenReturn(permissionIds);

        // 执行测试
        List<Long> result = roleService.getRolePermissions(1L);

        // 验证结果
        assertNotNull(result);
        assertEquals(permissionIds, result);

        // 验证方法调用
        verify(roleDomainService).findById(1L);
        verify(roleDomainService).getRolePermissionIds(1L);
    }

    @Test
    @DisplayName("测试获取角色权限失败 - 角色不存在")
    void testGetRolePermissionsFailureRoleNotFound() {
        // 准备模拟数据
        when(roleDomainService.findById(1L)).thenReturn(Optional.empty());

        // 执行测试并验证异常
        assertThrows(EntityNotFoundException.class, () -> roleService.getRolePermissions(1L));

        // 验证方法调用
        verify(roleDomainService).findById(1L);
        verify(roleDomainService, never()).getRolePermissionIds(anyLong());
    }

    @Test
    @DisplayName("测试分页查询角色成功")
    void testGetRolesSuccess() {
        // 准备模拟数据
        Pageable pageable = PageRequest.of(0, 10);
        List<RoleEntity> roleEntities = Collections.singletonList(testRole);
        Page<RoleEntity> rolePage = new PageImpl<>(roleEntities, pageable, 1);
        
        when(roleRepository.findAll(pageable)).thenReturn(rolePage);
        when(roleMapper.entityToResponse(any(RoleEntity.class))).thenReturn(roleResponse);

        // 执行测试
        Page<RoleResponse> responsePage = roleService.getRoles(pageable);

        // 验证结果
        assertNotNull(responsePage);
        assertEquals(1, responsePage.getTotalElements());
        assertEquals(1, responsePage.getContent().size());
        assertEquals(1L, responsePage.getContent().get(0).getId());
        assertEquals("测试角色", responsePage.getContent().get(0).getName());

        // 验证方法调用
        verify(roleRepository).findAll(pageable);
        verify(roleMapper).entityToResponse(any(RoleEntity.class));
    }
}