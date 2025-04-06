package com.example.backend_scaffold.application.service;

import com.example.backend_scaffold.application.dto.permission.PermissionRequest;
import com.example.backend_scaffold.application.dto.permission.PermissionResponse;
import com.example.backend_scaffold.domain.model.enums.PermissionType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * 权限服务接口
 * <p>
 * 提供权限的创建、查询、更新、删除等功能
 * </p>
 *
 * @author example
 */
public interface PermissionService {

    /**
     * 创建权限
     *
     * @param permissionRequest 权限请求DTO
     * @return 权限响应DTO
     */
    PermissionResponse createPermission(PermissionRequest permissionRequest);

    /**
     * 更新权限
     *
     * @param id                权限ID
     * @param permissionRequest 权限请求DTO
     * @return 权限响应DTO
     */
    PermissionResponse updatePermission(Long id, PermissionRequest permissionRequest);

    /**
     * 根据ID查询权限
     *
     * @param id 权限ID
     * @return 权限响应DTO
     */
    PermissionResponse getPermissionById(Long id);
    
    /**
     * 获取权限
     *
     * @param id 权限ID
     * @return 权限响应DTO
     */
    PermissionResponse getPermission(Long id);

    /**
     * 根据编码查询权限
     *
     * @param code 权限编码
     * @return 权限响应DTO
     */
    PermissionResponse getPermissionByCode(String code);

    /**
     * 查询所有权限
     *
     * @return 权限响应DTO列表
     */
    List<PermissionResponse> getAllPermissions();

    /**
     * 根据类型查询权限
     *
     * @param type 权限类型
     * @return 权限响应DTO列表
     */
    List<PermissionResponse> getPermissionsByType(PermissionType type);

    /**
     * 根据父权限ID查询权限
     *
     * @param parentId 父权限ID
     * @return 权限响应DTO列表
     */
    List<PermissionResponse> getPermissionsByParentId(Long parentId);

    /**
     * 删除权限
     *
     * @param id 权限ID
     * @return 是否删除成功
     */
    boolean deletePermission(Long id);

    /**
     * 启用/禁用权限
     *
     * @param id        权限ID
     * @param isEnabled 是否启用
     * @return 权限响应DTO
     */
    PermissionResponse togglePermissionStatus(Long id, boolean isEnabled);
    
    /**
     * 分页获取权限列表
     *
     * @param pageable 分页参数
     * @return 权限响应分页
     */
    Page<PermissionResponse> getPermissions(Pageable pageable);
}