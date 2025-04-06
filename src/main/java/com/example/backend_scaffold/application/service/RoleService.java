package com.example.backend_scaffold.application.service;

import com.example.backend_scaffold.application.dto.role.RolePermissionRequest;
import com.example.backend_scaffold.application.dto.role.RoleRequest;
import com.example.backend_scaffold.application.dto.role.RoleResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * 角色服务接口
 * <p>
 * 提供角色的创建、查询、更新、删除等功能
 * </p>
 *
 * @author example
 */
public interface RoleService {

    /**
     * 创建角色
     *
     * @param roleRequest 角色请求DTO
     * @return 角色响应DTO
     */
    RoleResponse createRole(RoleRequest roleRequest);

    /**
     * 更新角色
     *
     * @param id          角色ID
     * @param roleRequest 角色请求DTO
     * @return 角色响应DTO
     */
    RoleResponse updateRole(Long id, RoleRequest roleRequest);

    /**
     * 根据ID查询角色
     *
     * @param id 角色ID
     * @return 角色响应DTO
     */
    RoleResponse getRoleById(Long id);

    /**
     * 根据编码查询角色
     *
     * @param code 角色编码
     * @return 角色响应DTO
     */
    RoleResponse getRoleByCode(String code);

    /**
     * 查询所有角色
     *
     * @return 角色响应DTO列表
     */
    List<RoleResponse> getAllRoles();

    /**
     * 删除角色
     *
     * @param id 角色ID
     */
    void deleteRole(Long id);

    /**
     * 为角色分配权限
     *
     * @param roleId                角色ID
     * @param rolePermissionRequest 角色权限请求DTO
     * @return 角色响应DTO
     */
    RoleResponse assignPermissionsToRole(Long roleId, RolePermissionRequest rolePermissionRequest);

    /**
     * 获取角色的所有权限
     *
     * @param roleId 角色ID
     * @return 角色响应DTO，包含权限信息
     */
    List<Long> getRolePermissions(Long roleId);

    /**
     * 分页获取角色列表
     * 
     * @param pageable
     * @return 角色响应分页
     */
    Page<RoleResponse> getRoles(Pageable pageable);
}