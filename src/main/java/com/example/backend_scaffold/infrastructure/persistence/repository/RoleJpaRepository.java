package com.example.backend_scaffold.infrastructure.persistence.repository;

import com.example.backend_scaffold.infrastructure.persistence.entity.RoleJpaEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * 角色JPA仓储
 * <p>
 * 提供角色实体的数据访问操作
 * </p>
 *
 * @author example
 */
@Repository
public interface RoleJpaRepository extends JpaRepository<RoleJpaEntity, Long> {

    /**
     * 根据角色名称查找角色
     *
     * @param name 角色名称
     * @return 角色实体
     */
    Optional<RoleJpaEntity> findByName(String name);

    /**
     * 根据角色编码查找角色
     *
     * @param code 角色编码
     * @return 角色实体
     */
    Optional<RoleJpaEntity> findByCode(String code);

    /**
     * 检查角色名称是否存在
     *
     * @param name 角色名称
     * @return 是否存在
     */
    boolean existsByName(String name);

    /**
     * 检查角色编码是否存在
     *
     * @param code 角色编码
     * @return 是否存在
     */
    boolean existsByCode(String code);

    /**
     * 根据启用状态查找角色
     *
     * @param isEnabled 启用状态
     * @return 角色列表
     */
    List<RoleJpaEntity> findByIsEnabled(Boolean isEnabled);

    /**
     * 分页查询角色列表
     *
     * @param pageable 分页参数
     * @return 角色实体分页
     */
    Page<RoleJpaEntity> findAll(Pageable pageable);

    /**
     * 根据关键字搜索角色
     *
     * @param keyword  关键字
     * @param pageable 分页参数
     * @return 角色实体分页
     */
    @Query("SELECT r FROM RoleJpaEntity r WHERE r.name LIKE %:keyword% OR r.code LIKE %:keyword% OR r.description LIKE %:keyword%")
    Page<RoleJpaEntity> searchRoles(@Param("keyword") String keyword, Pageable pageable);
}