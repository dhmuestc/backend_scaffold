package com.example.backend_scaffold.domain.service;

import com.example.backend_scaffold.domain.model.entity.PermissionEntity;
import com.example.backend_scaffold.domain.model.enums.PermissionType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

/**
 * 权限领域服务接口
 * <p>
 * 定义权限相关的业务逻辑
 * </p>
 *
 * @author example
 */
public interface PermissionDomainService {

    /**
     * 创建权限
     *
     * @param permissionEntity 权限实体
     * @return 创建后的权限实体
     */
    PermissionEntity createPermission(PermissionEntity permissionEntity);

    /**
     * 更新权限
     *
     * @param permissionEntity 权限实体
     * @return 更新后的权限实体
     */
    PermissionEntity updatePermission(PermissionEntity permissionEntity);

    /**
     * 删除权限
     *
     * @param id 权限ID
     */
    void deletePermission(Long id);

    /**
     * 根据ID查询权限
     *
     * @param id 权限ID
     * @return 权限实体Optional包装
     */
    Optional<PermissionEntity> findById(Long id);

    /**
     * 根据权限编码查询权限
     *
     * @param code 权限编码
     * @return 权限实体Optional包装
     */
    Optional<PermissionEntity> findByCode(String code);

    /**
     * 根据权限名称查询权限
     *
     * @param name 权限名称
     * @return 权限实体Optional包装
     */
    Optional<PermissionEntity> findByName(String name);

    /**
     * 根据权限类型查询权限列表
     *
     * @param type 权限类型
     * @return 权限实体列表
     */
    List<PermissionEntity> findByType(PermissionType type);

    /**
     * 根据URL和方法查询权限（用于API权限）
     *
     * @param url    URL
     * @param method 方法
     * @return 权限实体Optional包装
     */
    Optional<PermissionEntity> findByUrlAndMethod(String url, String method);

    /**
     * 根据父权限ID查询子权限列表
     *
     * @param parentId 父权限ID
     * @return 权限实体列表
     */
    List<PermissionEntity> findByParentId(Long parentId);

    /**
     * 查询所有权限
     *
     * @return 权限实体列表
     */
    List<PermissionEntity> findAll();

     /**
     * 分页查询所有权限
     *
     * @param pageable 分页参数
     * @return 权限实体分页结果
     */
    Page<PermissionEntity> findAll(Pageable pageable);

    /**
     * 查询所有启用的权限
     *
     * @return 权限实体列表
     */
    List<PermissionEntity> findByIsEnabled(Boolean isEnabled);

    /**
     * 启用权限
     *
     * @param id 权限ID
     * @return 是否启用成功
     */
    boolean enablePermission(Long id);

    /**
     * 禁用权限
     *
     * @param id 权限ID
     * @return 是否禁用成功
     */
    boolean disablePermission(Long id);
}