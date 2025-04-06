package com.example.backend_scaffold.domain.repository;

import com.example.backend_scaffold.domain.model.entity.RolePermissionEntity;

import java.util.List;
import java.util.Optional;

/**
 * 角色权限关联仓储接口
 * <p>
 * 定义角色权限关联实体的存储和查询操作
 * </p>
 *
 * @author example
 */
public interface RolePermissionRepository {

    /**
     * 保存角色权限关联实体
     *
     * @param rolePermissionEntity 角色权限关联实体
     * @return 保存后的角色权限关联实体
     */
    RolePermissionEntity save(RolePermissionEntity rolePermissionEntity);

    /**
     * 根据ID查询角色权限关联
     *
     * @param id 角色权限关联ID
     * @return 角色权限关联实体Optional包装
     */
    Optional<RolePermissionEntity> findById(Long id);

    /**
     * 根据角色ID查询角色权限关联列表
     *
     * @param roleId 角色ID
     * @return 角色权限关联实体列表
     */
    List<RolePermissionEntity> findByRoleId(Long roleId);

    /**
     * 根据权限ID查询角色权限关联列表
     *
     * @param permissionId 权限ID
     * @return 角色权限关联实体列表
     */
    List<RolePermissionEntity> findByPermissionId(Long permissionId);

    /**
     * 根据角色ID和权限ID查询角色权限关联
     *
     * @param roleId 角色ID
     * @param permissionId 权限ID
     * @return 角色权限关联实体Optional包装
     */
    Optional<RolePermissionEntity> findByRoleIdAndPermissionId(Long roleId, Long permissionId);

    /**
     * 删除角色权限关联
     *
     * @param id 角色权限关联ID
     */
    void deleteById(Long id);

    /**
     * 根据角色ID删除角色权限关联
     *
     * @param roleId 角色ID
     */
    void deleteByRoleId(Long roleId);

    /**
     * 根据权限ID删除角色权限关联
     *
     * @param permissionId 权限ID
     */
    void deleteByPermissionId(Long permissionId);

    /**
     * 根据角色ID和权限ID删除角色权限关联
     *
     * @param roleId 角色ID
     * @param permissionId 权限ID
     */
    void deleteByRoleIdAndPermissionId(Long roleId, Long permissionId);
    
    /**
     * 检查角色权限关联是否存在
     *
     * @param roleId 角色ID
     * @param permissionId 权限ID
     * @return 是否存在
     */
    boolean existsByRoleIdAndPermissionId(Long roleId, Long permissionId);
}