package com.example.backend_scaffold.infrastructure.persistence.repository;

import com.example.backend_scaffold.infrastructure.persistence.entity.RoleJpaEntity;
import com.example.backend_scaffold.infrastructure.persistence.entity.UserJpaEntity;
import com.example.backend_scaffold.domain.model.enums.UserStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * 用户JPA仓储
 * <p>
 * 提供用户实体的数据访问操作
 * </p>
 *
 * @author example
 */
@Repository
public interface UserJpaRepository extends JpaRepository<UserJpaEntity, Long> {

    /**
     * 根据用户名查找用户
     *
     * @param username 用户名
     * @return 用户实体
     */
    Optional<UserJpaEntity> findByUsername(String username);

    /**
     * 根据邮箱查找用户
     *
     * @param email 邮箱
     * @return 用户实体
     */
    Optional<UserJpaEntity> findByEmail(String email);

    /**
     * 根据电话查找用户
     * 
     * @param phone
     * @return 用户实体
     */
    Optional<UserJpaEntity> findByPhone(String phone);

    /**
     * 根据用户名或邮箱查找用户
     *
     * @param username 用户名
     * @param email    邮箱
     * @return 用户实体
     */
    Optional<UserJpaEntity> findByUsernameOrEmail(String username, String email);

    /**
     * 检查用户名是否存在
     *
     * @param username 用户名
     * @return 是否存在
     */
    boolean existsByUsername(String username);

    /**
     * 检查邮箱是否存在
     *
     * @param email 邮箱
     * @return 是否存在
     */
    boolean existsByEmail(String email);

    /**
     * 检查电话是否存在
     * 
     * @param phone
     * @return 是否存在
     */
    boolean existsByPhone(String phone);

    /**
     * 根据状态查找用户列表
     *
     * @param status 用户状态
     * @return 用户实体列表
     */
    List<UserJpaEntity> findByStatus(UserStatus status);

    /**
     * 分页查询指定状态的用户列表
     *
     * @param status 用户状态
     * @param pageable 分页参数
     * @return 用户实体分页
     */
    Page<UserJpaEntity> findByStatus(UserStatus status, Pageable pageable);

    /**
     * 分页查询用户列表
     *
     * @param pageable 分页参数
     * @return 用户实体分页
     */
    Page<UserJpaEntity> findAll(Pageable pageable);

    /**
     * 根据关键字搜索用户
     *
     * @param keyword  关键字
     * @param pageable 分页参数
     * @return 用户实体分页
     */
    @Query("SELECT u FROM UserJpaEntity u WHERE u.username LIKE %:keyword% OR u.email LIKE %:keyword% OR u.nickname LIKE %:keyword%")
    Page<UserJpaEntity> searchUsers(@Param("keyword") String keyword, Pageable pageable);
    
    /**
     * 根据用户ID查询用户的角色列表
     *
     * @param userId 用户ID
     * @return 角色实体列表
     */
    @Query("SELECT r FROM RoleJpaEntity r JOIN UserRoleJpaEntity ur ON r.id = ur.roleId WHERE ur.userId = :userId AND ur.isEnabled = true AND r.isEnabled = true")
    List<RoleJpaEntity> findRolesByUserId(@Param("userId") Long userId);
}