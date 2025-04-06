package com.example.backend_scaffold.infrastructure.persistence.repository;

import com.example.backend_scaffold.infrastructure.persistence.entity.UserRoleJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * 用户角色关联JPA仓储
 * <p>
 * 提供用户角色关联实体的数据访问操作
 * </p>
 *
 * @author example
 */
@Repository
public interface UserRoleJpaRepository extends JpaRepository<UserRoleJpaEntity, Long> {

    /**
     * 根据用户ID查询用户角色关联列表
     *
     * @param userId 用户ID
     * @return 用户角色关联实体列表
     */
    List<UserRoleJpaEntity> findByUserId(Long userId);

    /**
     * 根据角色ID查询用户角色关联列表
     *
     * @param roleId 角色ID
     * @return 用户角色关联实体列表
     */
    List<UserRoleJpaEntity> findByRoleId(Long roleId);

    /**
     * 根据用户ID和角色ID查询用户角色关联
     *
     * @param userId 用户ID
     * @param roleId 角色ID
     * @return 用户角色关联实体Optional包装
     */
    Optional<UserRoleJpaEntity> findByUserIdAndRoleId(Long userId, Long roleId);

    /**
     * 根据用户ID删除用户角色关联
     *
     * @param userId 用户ID
     */
    @Modifying
    @Query("DELETE FROM UserRoleJpaEntity ur WHERE ur.userId = :userId")
    void deleteByUserId(@Param("userId") Long userId);

    /**
     * 根据角色ID删除用户角色关联
     *
     * @param roleId 角色ID
     */
    @Modifying
    @Query("DELETE FROM UserRoleJpaEntity ur WHERE ur.roleId = :roleId")
    void deleteByRoleId(@Param("roleId") Long roleId);

    /**
     * 根据用户ID和角色ID删除用户角色关联
     *
     * @param userId 用户ID
     * @param roleId 角色ID
     */
    @Modifying
    @Query("DELETE FROM UserRoleJpaEntity ur WHERE ur.userId = :userId AND ur.roleId = :roleId")
    void deleteByUserIdAndRoleId(@Param("userId") Long userId, @Param("roleId") Long roleId);
}