package com.example.backend_scaffold.domain.service;

import com.example.backend_scaffold.domain.model.entity.RoleEntity;
import com.example.backend_scaffold.domain.model.entity.RolePermissionEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

/**
 * 角色领域服务接口
 * <p>
 * 定义角色相关的业务逻辑
 * </p>
 *
 * @author example
 */
public interface RoleDomainService {

    /**
     * 创建角色
     *
     * @param roleEntity 角色实体
     * @return 创建后的角色实体
     */
    RoleEntity createRole(RoleEntity roleEntity);

    /**
     * 更新角色
     *
     * @param roleEntity 角色实体
     * @return 更新后的角色实体
     */
    RoleEntity updateRole(RoleEntity roleEntity);

    /**
     * 删除角色
     *
     * @param id 角色ID
     */
    void deleteRole(Long id);

    /**
     * 根据ID查询角色
     *
     * @param id 角色ID
     * @return 角色实体Optional包装
     */
    Optional<RoleEntity> findById(Long id);

    /**
     * 根据角色编码查询角色
     *
     * @param code 角色编码
     * @return 角色实体Optional包装
     */
    Optional<RoleEntity> findByCode(String code);

    /**
     * 根据角色名称查询角色
     *
     * @param name 角色名称
     * @return 角色实体Optional包装
     */
    Optional<RoleEntity> findByName(String name);

    /**
     * 查询所有角色
     *
     * @return 角色实体列表
     */
    List<RoleEntity> findAll();

    /**
     * 分页查询所有角色
     *
     * @param pageable 分页参数
     * @return 角色实体列表
     */
    Page<RoleEntity> findAll(Pageable pageable);

    /**
     * 查询所有启用的角色
     *
     * @return 角色实体列表
     */
    List<RoleEntity> findByIsEnabled(Boolean isEnabled);

    /**
     * 为角色分配权限
     *
     * @param roleId       角色ID
     * @param permissionId 权限ID
     * @return 角色权限关联实体
     */
    RolePermissionEntity assignPermission(Long roleId, Long permissionId);

    /**
     * 移除角色权限
     *
     * @param roleId       角色ID
     * @param permissionId 权限ID
     */
    void removePermission(Long roleId, Long permissionId);

    /**
     * 获取角色的所有权限ID
     *
     * @param roleId 角色ID
     * @return 权限ID列表
     */
    List<Long> getRolePermissionIds(Long roleId);

    /**
     * 启用角色
     *
     * @param roleId 角色ID
     * @return 是否启用成功
     */
    boolean enableRole(Long roleId);

    /**
     * 禁用角色
     *
     * @param roleId 角色ID
     * @return 是否禁用成功
     */
    boolean disableRole(Long roleId);
}