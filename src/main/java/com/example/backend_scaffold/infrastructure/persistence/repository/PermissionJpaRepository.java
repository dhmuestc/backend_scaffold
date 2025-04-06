package com.example.backend_scaffold.infrastructure.persistence.repository;

import com.example.backend_scaffold.domain.model.enums.PermissionType;
import com.example.backend_scaffold.infrastructure.persistence.entity.PermissionJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * 权限JPA仓储
 * <p>
 * 提供权限实体的数据访问操作
 * </p>
 *
 * @author example
 */
@Repository
public interface PermissionJpaRepository extends JpaRepository<PermissionJpaEntity, Long> {

    List<PermissionJpaEntity> findByUrl(String url);


    /**
     * 根据权限编码查询权限
     *
     * @param code 权限编码
     * @return 权限实体Optional包装
     */
    Optional<PermissionJpaEntity> findByCode(String code);

    /**
     * 根据权限类型查询权限列表
     *
     * @param type 权限类型
     * @return 权限实体列表
     */
    List<PermissionJpaEntity> findByType(PermissionType type);

    /**
     * 根据父权限ID查询权限列表
     *
     * @param parentId 父权限ID
     * @return 权限实体列表
     */
    List<PermissionJpaEntity> findByParentId(Long parentId);

    /**
     * 根据URL和请求方法查询权限
     *
     * @param url    URL
     * @param method 请求方法
     * @return 权限实体Optional包装
     */
    Optional<PermissionJpaEntity> findByUrlAndMethod(String url, String method);

    /**
     * 检查权限编码是否存在
     *
     * @param code 权限编码
     * @return 是否存在
     */
    boolean existsByCode(String code);

    /**
     * 查询所有启用的权限
     *
     * @return 权限实体列表
     */
    List<PermissionJpaEntity> findByIsEnabledTrue();

    /**
     * 检查权限名称是否存在
     *
     * @param name 权限名称
     * @return 是否存在
     */
    boolean existsByName(String name);

    /**
     * 根据权限名称查询权限
     *
     * @param name 权限名称
     * @return 权限实体Optional包装
     */
    Optional<PermissionJpaEntity> findByName(String name);

    /**
     * 根据角色ID查询权限列表
     *
     * @param roleId 角色ID
     * @return 权限实体列表
     */
    @Query("SELECT p FROM PermissionJpaEntity p JOIN RolePermissionJpaEntity rp ON p.id = rp.permissionId WHERE rp.roleId = :roleId AND rp.isEnabled = true AND p.isEnabled = true")
    List<PermissionJpaEntity> findByRoleId(@Param("roleId") Long roleId);

    /**
     * 根据用户ID查询权限列表
     *
     * @param userId 用户ID
     * @return 权限实体列表
     */
    @Query("SELECT DISTINCT p FROM PermissionJpaEntity p JOIN RolePermissionJpaEntity rp ON p.id = rp.permissionId JOIN UserRoleJpaEntity ur ON rp.roleId = ur.roleId WHERE ur.userId = :userId AND ur.isEnabled = true AND rp.isEnabled = true AND p.isEnabled = true")
    List<PermissionJpaEntity> findByUserId(@Param("userId") Long userId);
}