package com.example.backend_scaffold.application.service;

import com.example.backend_scaffold.application.dto.permission.PermissionRequest;
import com.example.backend_scaffold.application.dto.permission.PermissionResponse;
import com.example.backend_scaffold.application.exception.EntityNotFoundException;
import com.example.backend_scaffold.application.mapper.PermissionMapper;
import com.example.backend_scaffold.application.service.impl.PermissionServiceImpl;
import com.example.backend_scaffold.domain.model.entity.PermissionEntity;
import com.example.backend_scaffold.domain.model.enums.PermissionType;
import com.example.backend_scaffold.domain.service.PermissionDomainService;
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

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PermissionServiceTest {

    @Mock
    private PermissionDomainService permissionDomainService;

    @Mock
    private PermissionMapper permissionMapper;

    @InjectMocks
    private PermissionServiceImpl permissionService;

    private PermissionEntity testPermission;
    private PermissionRequest permissionRequest;
    private PermissionResponse permissionResponse;

    @BeforeEach
    void setUp() {
        // 创建测试权限实体
        testPermission = new PermissionEntity();
        testPermission.setId(1L);
        testPermission.setName("测试权限");
        testPermission.setCode("TEST_PERMISSION");
        testPermission.setDescription("这是一个测试权限");
        testPermission.setType(PermissionType.MENU);
        testPermission.setParentId(0L);
        testPermission.setSortOrder(1);

        // 创建权限请求
        permissionRequest = new PermissionRequest();
        permissionRequest.setName("测试权限");
        permissionRequest.setCode("TEST_PERMISSION");
        permissionRequest.setDescription("这是一个测试权限");
        permissionRequest.setType(PermissionType.MENU);
        permissionRequest.setParentId(0L);
        permissionRequest.setSortOrder(1);

        // 创建权限响应
        permissionResponse = new PermissionResponse();
        permissionResponse.setId(1L);
        permissionResponse.setName("测试权限");
        permissionResponse.setCode("TEST_PERMISSION");
        permissionResponse.setDescription("这是一个测试权限");
        permissionResponse.setType(PermissionType.MENU);
        permissionResponse.setParentId(0L);
        permissionResponse.setSortOrder(1);
    }

    @Test
    @DisplayName("测试创建权限成功")
    void testCreatePermissionSuccess() {
        // 准备模拟数据
        when(permissionMapper.toEntity(any(PermissionRequest.class))).thenReturn(testPermission);
        when(permissionDomainService.createPermission(any(PermissionEntity.class))).thenReturn(testPermission);
        when(permissionMapper.entityToResponse(any(PermissionEntity.class))).thenReturn(permissionResponse);

        // 执行测试
        PermissionResponse response = permissionService.createPermission(permissionRequest);

        // 验证结果
        assertNotNull(response);
        assertEquals(1L, response.getId());
        assertEquals("测试权限", response.getName());
        assertEquals("TEST_PERMISSION", response.getCode());
        assertEquals(PermissionType.MENU.name(), response.getType());

        // 验证方法调用
        verify(permissionMapper).toEntity(any(PermissionRequest.class));
        verify(permissionDomainService).createPermission(any(PermissionEntity.class));
        verify(permissionMapper).entityToResponse(any(PermissionEntity.class));
    }

    @Test
    @DisplayName("测试更新权限成功")
    void testUpdatePermissionSuccess() {
        // 准备模拟数据
        when(permissionDomainService.findById(1L)).thenReturn(Optional.of(testPermission));
        when(permissionMapper.updateEntity(any(PermissionRequest.class), any(PermissionEntity.class))).thenReturn(testPermission);
        when(permissionDomainService.updatePermission(any(PermissionEntity.class))).thenReturn(testPermission);
        when(permissionMapper.entityToResponse(any(PermissionEntity.class))).thenReturn(permissionResponse);

        // 执行测试
        PermissionResponse response = permissionService.updatePermission(1L, permissionRequest);

        // 验证结果
        assertNotNull(response);
        assertEquals(1L, response.getId());
        assertEquals("测试权限", response.getName());
        assertEquals("TEST_PERMISSION", response.getCode());

        // 验证方法调用
        verify(permissionDomainService).findById(1L);
        verify(permissionMapper).updateEntity(any(PermissionRequest.class), any(PermissionEntity.class));
        verify(permissionDomainService).updatePermission(any(PermissionEntity.class));
        verify(permissionMapper).entityToResponse(any(PermissionEntity.class));
    }

    @Test
    @DisplayName("测试更新权限失败 - 权限不存在")
    void testUpdatePermissionFailurePermissionNotFound() {
        // 准备模拟数据
        when(permissionDomainService.findById(1L)).thenReturn(Optional.empty());

        // 执行测试并验证异常
        assertThrows(EntityNotFoundException.class, () -> permissionService.updatePermission(1L, permissionRequest));

        // 验证方法调用
        verify(permissionDomainService).findById(1L);
        verify(permissionMapper, never()).updateEntity(any(PermissionRequest.class), any(PermissionEntity.class));
        verify(permissionDomainService, never()).updatePermission(any(PermissionEntity.class));
    }

    @Test
    @DisplayName("测试根据ID查询权限成功")
    void testGetPermissionByIdSuccess() {
        // 准备模拟数据
        when(permissionDomainService.findById(1L)).thenReturn(Optional.of(testPermission));
        when(permissionMapper.entityToResponse(any(PermissionEntity.class))).thenReturn(permissionResponse);

        // 执行测试
        PermissionResponse response = permissionService.getPermissionById(1L);

        // 验证结果
        assertNotNull(response);
        assertEquals(1L, response.getId());
        assertEquals("测试权限", response.getName());
        assertEquals("TEST_PERMISSION", response.getCode());

        // 验证方法调用
        verify(permissionDomainService).findById(1L);
        verify(permissionMapper).entityToResponse(any(PermissionEntity.class));
    }

    @Test
    @DisplayName("测试根据ID查询权限失败 - 权限不存在")
    void testGetPermissionByIdFailurePermissionNotFound() {
        // 准备模拟数据
        when(permissionDomainService.findById(1L)).thenReturn(Optional.empty());

        // 执行测试并验证异常
        assertThrows(EntityNotFoundException.class, () -> permissionService.getPermissionById(1L));

        // 验证方法调用
        verify(permissionDomainService).findById(1L);
        verify(permissionMapper, never()).entityToResponse(any(PermissionEntity.class));
    }

    @Test
    @DisplayName("测试根据编码查询权限成功")
    void testGetPermissionByCodeSuccess() {
        // 准备模拟数据
        when(permissionDomainService.findByCode("TEST_PERMISSION")).thenReturn(Optional.of(testPermission));
        when(permissionMapper.entityToResponse(any(PermissionEntity.class))).thenReturn(permissionResponse);

        // 执行测试
        PermissionResponse response = permissionService.getPermissionByCode("TEST_PERMISSION");

        // 验证结果
        assertNotNull(response);
        assertEquals(1L, response.getId());
        assertEquals("测试权限", response.getName());
        assertEquals("TEST_PERMISSION", response.getCode());

        // 验证方法调用
        verify(permissionDomainService).findByCode("TEST_PERMISSION");
        verify(permissionMapper).entityToResponse(any(PermissionEntity.class));
    }

    @Test
    @DisplayName("测试根据编码查询权限失败 - 权限不存在")
    void testGetPermissionByCodeFailurePermissionNotFound() {
        // 准备模拟数据
        when(permissionDomainService.findByCode("TEST_PERMISSION")).thenReturn(Optional.empty());

        // 执行测试并验证异常
        assertThrows(EntityNotFoundException.class, () -> permissionService.getPermissionByCode("TEST_PERMISSION"));

        // 验证方法调用
        verify(permissionDomainService).findByCode("TEST_PERMISSION");
        verify(permissionMapper, never()).entityToResponse(any(PermissionEntity.class));
    }

    @Test
    @DisplayName("测试查询所有权限成功")
    void testGetAllPermissionsSuccess() {
        // 准备模拟数据
        List<PermissionEntity> permissionEntities = Collections.singletonList(testPermission);
        List<PermissionResponse> permissionResponses = Collections.singletonList(permissionResponse);
        
        when(permissionDomainService.findAll()).thenReturn(permissionEntities);
        when(permissionMapper.entitiesToResponseList(permissionEntities)).thenReturn(permissionResponses);

        // 执行测试
        List<PermissionResponse> responses = permissionService.getAllPermissions();

        // 验证结果
        assertNotNull(responses);
        assertEquals(1, responses.size());
        assertEquals(1L, responses.get(0).getId());
        assertEquals("测试权限", responses.get(0).getName());
        assertEquals("TEST_PERMISSION", responses.get(0).getCode());

        // 验证方法调用
        verify(permissionDomainService).findAll();
        verify(permissionMapper).entitiesToResponseList(permissionEntities);
    }

    @Test
    @DisplayName("测试根据类型查询权限成功")
    void testGetPermissionsByTypeSuccess() {
        // 准备模拟数据
        List<PermissionEntity> permissionEntities = Collections.singletonList(testPermission);
        List<PermissionResponse> permissionResponses = Collections.singletonList(permissionResponse);
        
        when(permissionDomainService.findByType(PermissionType.MENU)).thenReturn(permissionEntities);
        when(permissionMapper.entitiesToResponseList(permissionEntities)).thenReturn(permissionResponses);

        // 执行测试
        List<PermissionResponse> responses = permissionService.getPermissionsByType(PermissionType.MENU);

        // 验证结果
        assertNotNull(responses);
        assertEquals(1, responses.size());
        assertEquals(1L, responses.get(0).getId());
        assertEquals("测试权限", responses.get(0).getName());
        assertEquals("TEST_PERMISSION", responses.get(0).getCode());

        // 验证方法调用
        verify(permissionDomainService).findByType(PermissionType.MENU);
        verify(permissionMapper).entitiesToResponseList(permissionEntities);
    }

    @Test
    @DisplayName("测试根据父ID查询权限成功")
    void testGetPermissionsByParentIdSuccess() {
        // 准备模拟数据
        List<PermissionEntity> permissionEntities = Collections.singletonList(testPermission);
        List<PermissionResponse> permissionResponses = Collections.singletonList(permissionResponse);
        
        when(permissionDomainService.findByParentId(0L)).thenReturn(permissionEntities);
        when(permissionMapper.entitiesToResponseList(permissionEntities)).thenReturn(permissionResponses);

        // 执行测试
        List<PermissionResponse> responses = permissionService.getPermissionsByParentId(0L);

        // 验证结果
        assertNotNull(responses);
        assertEquals(1, responses.size());
        assertEquals(1L, responses.get(0).getId());
        assertEquals("测试权限", responses.get(0).getName());
        assertEquals("TEST_PERMISSION", responses.get(0).getCode());

        // 验证方法调用
        verify(permissionDomainService).findByParentId(0L);
        verify(permissionMapper).entitiesToResponseList(permissionEntities);
    }

    @Test
    @DisplayName("测试删除权限成功")
    void testDeletePermissionSuccess() {
        // 准备模拟数据
        when(permissionDomainService.findById(1L)).thenReturn(Optional.of(testPermission));
        doNothing().when(permissionDomainService).deletePermission(1L);

        // 执行测试
        permissionService.deletePermission(1L);

        // 验证方法调用
        verify(permissionDomainService).findById(1L);
        verify(permissionDomainService).deletePermission(1L);
    }

    @Test
    @DisplayName("测试删除权限失败 - 权限不存在")
    void testDeletePermissionFailurePermissionNotFound() {
        // 准备模拟数据
        when(permissionDomainService.findById(1L)).thenReturn(Optional.empty());

        // 执行测试并验证异常
        assertThrows(EntityNotFoundException.class, () -> permissionService.deletePermission(1L));

        // 验证方法调用
        verify(permissionDomainService).findById(1L);
        verify(permissionDomainService, never()).deletePermission(anyLong());
    }

    @Test
    @DisplayName("测试分页查询权限成功")
    void testGetPermissionsPageSuccess() {
        // 准备模拟数据
        Pageable pageable = PageRequest.of(0, 10);
        List<PermissionEntity> permissionEntities = Collections.singletonList(testPermission);
        Page<PermissionEntity> permissionPage = new PageImpl<>(permissionEntities, pageable, 1);
        
        when(permissionDomainService.findAll(pageable)).thenReturn(permissionPage);
        when(permissionMapper.entityToResponse(any(PermissionEntity.class))).thenReturn(permissionResponse);

        // 执行测试
        Page<PermissionResponse> responsePage = permissionService.getPermissions(pageable);

        // 验证结果
        assertNotNull(responsePage);
        assertEquals(1, responsePage.getTotalElements());
        assertEquals(1, responsePage.getContent().size());
        assertEquals(1L, responsePage.getContent().get(0).getId());
        assertEquals("测试权限", responsePage.getContent().get(0).getName());

        // 验证方法调用
        verify(permissionDomainService).findAll(pageable);
        verify(permissionMapper).entityToResponse(any(PermissionEntity.class));
    }
}