package com.example.backend_scaffold.infrastructure.persistence.repository;

import com.example.backend_scaffold.infrastructure.persistence.entity.RolePermissionJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 角色权限JPA仓储
 * <p>
 * 提供角色权限关联实体的数据访问操作
 * </p>
 *
 * @author example
 */
@Repository
public interface RolePermissionJpaRepository extends JpaRepository<RolePermissionJpaEntity, Long> {

    /**
     * 根据角色ID查找角色权限关联列表
     *
     * @param roleId 角色ID
     * @return 角色权限关联实体列表
     */
    List<RolePermissionJpaEntity> findByRoleId(Long roleId);

    /**
     * 根据权限ID查找角色权限关联列表
     *
     * @param permissionId 权限ID
     * @return 角色权限关联实体列表
     */
    List<RolePermissionJpaEntity> findByPermissionId(Long permissionId);

    /**
     * 根据角色ID和权限ID查找角色权限关联
     *
     * @param roleId       角色ID
     * @param permissionId 权限ID
     * @return 角色权限关联实体
     */
    RolePermissionJpaEntity findByRoleIdAndPermissionId(Long roleId, Long permissionId);

    /**
     * 根据角色ID删除角色权限关联
     *
     * @param roleId 角色ID
     * @return 删除的记录数
     */
    @Modifying
    @Query("DELETE FROM RolePermissionJpaEntity rp WHERE rp.roleId = :roleId")
    int deleteByRoleId(@Param("roleId") Long roleId);

    /**
     * 根据权限ID删除角色权限关联
     *
     * @param permissionId 权限ID
     * @return 删除的记录数
     */
    @Modifying
    @Query("DELETE FROM RolePermissionJpaEntity rp WHERE rp.permissionId = :permissionId")
    int deleteByPermissionId(@Param("permissionId") Long permissionId);

    /**
     * 根据角色ID和权限ID删除角色权限关联
     *
     * @param roleId       角色ID
     * @param permissionId 权限ID
     * @return 删除的记录数
     */
    @Modifying
    @Query("DELETE FROM RolePermissionJpaEntity rp WHERE rp.roleId = :roleId AND rp.permissionId = :permissionId")
    int deleteByRoleIdAndPermissionId(@Param("roleId") Long roleId, @Param("permissionId") Long permissionId);

    /**
     * 检查角色权限关联是否存在
     *
     * @param roleId       角色ID
     * @param permissionId 权限ID
     * @return 是否存在
     */
    boolean existsByRoleIdAndPermissionId(Long roleId, Long permissionId);
}