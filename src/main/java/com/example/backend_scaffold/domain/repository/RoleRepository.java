package com.example.backend_scaffold.domain.repository;

import com.example.backend_scaffold.domain.model.entity.RoleEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

/**
 * 角色仓储接口
 * <p>
 * 定义角色实体的存储和查询操作
 * </p>
 *
 * @author example
 */
public interface RoleRepository {

    /**
     * 保存角色实体
     *
     * @param roleEntity 角色实体
     * @return 保存后的角色实体
     */
    RoleEntity save(RoleEntity roleEntity);

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
     * @return 角色实体分页
     */
    Page<RoleEntity> findAll(Pageable pageable);

    /**
     * 查询所有启用的角色
     *
     * @return 角色实体列表
     */
    List<RoleEntity> findByIsEnabled(Boolean isEnabled);

    /**
     * 删除角色
     *
     * @param id 角色ID
     */
    void deleteById(Long id);

    /**
     * 检查角色编码是否存在
     *
     * @param code 角色编码
     * @return 是否存在
     */
    boolean existsByCode(String code);

    /**
     * 检查角色名称是否存在
     *
     * @param name 角色名称
     * @return 是否存在
     */
    boolean existsByName(String name);
}