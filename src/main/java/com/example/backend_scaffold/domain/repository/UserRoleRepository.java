package com.example.backend_scaffold.domain.repository;

import com.example.backend_scaffold.domain.model.entity.UserRoleEntity;

import java.util.List;
import java.util.Optional;

/**
 * 用户角色关联仓储接口
 * <p>
 * 定义用户角色关联实体的存储和查询操作
 * </p>
 *
 * @author example
 */
public interface UserRoleRepository {

    /**
     * 保存用户角色关联实体
     *
     * @param userRoleEntity 用户角色关联实体
     * @return 保存后的用户角色关联实体
     */
    UserRoleEntity save(UserRoleEntity userRoleEntity);

    /**
     * 根据ID查询用户角色关联
     *
     * @param id 用户角色关联ID
     * @return 用户角色关联实体Optional包装
     */
    Optional<UserRoleEntity> findById(Long id);

    /**
     * 根据用户ID查询用户角色关联列表
     *
     * @param userId 用户ID
     * @return 用户角色关联实体列表
     */
    List<UserRoleEntity> findByUserId(Long userId);

    /**
     * 根据角色ID查询用户角色关联列表
     *
     * @param roleId 角色ID
     * @return 用户角色关联实体列表
     */
    List<UserRoleEntity> findByRoleId(Long roleId);

    /**
     * 根据用户ID和角色ID查询用户角色关联
     *
     * @param userId 用户ID
     * @param roleId 角色ID
     * @return 用户角色关联实体Optional包装
     */
    Optional<UserRoleEntity> findByUserIdAndRoleId(Long userId, Long roleId);

    /**
     * 删除用户角色关联
     *
     * @param id 用户角色关联ID
     */
    void deleteById(Long id);

    /**
     * 根据用户ID删除用户角色关联
     *
     * @param userId 用户ID
     */
    void deleteByUserId(Long userId);

    /**
     * 根据角色ID删除用户角色关联
     *
     * @param roleId 角色ID
     */
    void deleteByRoleId(Long roleId);

    /**
     * 根据用户ID和角色ID删除用户角色关联
     *
     * @param userId 用户ID
     * @param roleId 角色ID
     */
    void deleteByUserIdAndRoleId(Long userId, Long roleId);

    /**
     * 检查指定用户ID和角色ID的关联是否存在
     *
     * @param userId 用户ID
     * @param roleId 角色ID
     * @return 如果存在关联返回true，否则返回false
     */
    boolean existsByUserIdAndRoleId(Long userId, Long roleId);
}