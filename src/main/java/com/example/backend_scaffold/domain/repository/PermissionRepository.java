package com.example.backend_scaffold.domain.repository;

import com.example.backend_scaffold.domain.model.entity.PermissionEntity;
import com.example.backend_scaffold.domain.model.enums.PermissionType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

/**
 * 权限仓储接口
 * <p>
 * 定义权限实体的存储和查询操作
 * </p>
 *
 * @author example
 */
public interface PermissionRepository {

    /**
     * 保存权限实体
     *
     * @param permissionEntity 权限实体
     * @return 保存后的权限实体
     */
    PermissionEntity save(PermissionEntity permissionEntity);

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
     * 根据URL查询权限列表
     *
     * @param url URL
     * @return 权限实体列表
     */
    List<PermissionEntity> findByUrl(String url);

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
     * 删除权限
     *
     * @param id 权限ID
     */
    void deleteById(Long id);

    /**
     * 检查权限编码是否存在
     *
     * @param code 权限编码
     * @return 是否存在
     */
    boolean existsByCode(String code);

    /**
     * 检查权限名称是否存在
     *
     * @param name 权限名称
     * @return 是否存在
     */
    boolean existsByName(String name);
}