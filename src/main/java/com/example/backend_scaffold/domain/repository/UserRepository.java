package com.example.backend_scaffold.domain.repository;

import com.example.backend_scaffold.domain.model.entity.UserEntity;
import com.example.backend_scaffold.domain.model.enums.UserStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

/**
 * 用户仓储接口
 * <p>
 * 定义用户实体的存储和查询操作
 * </p>
 *
 * @author example
 */
public interface UserRepository {

    /**
     * 保存用户实体
     *
     * @param userEntity 用户实体
     * @return 保存后的用户实体
     */
    UserEntity save(UserEntity userEntity);

    /**
     * 根据ID查询用户
     *
     * @param id 用户ID
     * @return 用户实体Optional包装
     */
    Optional<UserEntity> findById(Long id);

    /**
     * 根据用户名查询用户
     *
     * @param username 用户名
     * @return 用户实体Optional包装
     */
    Optional<UserEntity> findByUsername(String username);

    /**
     * 根据邮箱查询用户
     *
     * @param email 邮箱
     * @return 用户实体Optional包装
     */
    Optional<UserEntity> findByEmail(String email);

    /**
     * 根据手机号查询用户
     *
     * @param phone 手机号
     * @return 用户实体Optional包装
     */
    Optional<UserEntity> findByPhone(String phone);

    /**
     * 根据状态查询用户列表
     *
     * @param status 用户状态
     * @return 用户实体列表
     */
    List<UserEntity> findByStatus(UserStatus status);
    
    /**
     * 根据状态分页查询用户列表
     *
     * @param status 用户状态
     * @param pageable 分页参数
     * @return 用户实体分页结果
     */
    Page<UserEntity> findByStatus(UserStatus status, Pageable pageable);

    /**
     * 查询所有用户
     *
     * @return 用户实体列表
     */
    List<UserEntity> findAll();
    
    /**
     * 分页查询所有用户
     *
     * @param pageable 分页参数
     * @return 用户实体分页结果
     */
    Page<UserEntity> findAll(Pageable pageable);

    /**
     * 删除用户
     *
     * @param id 用户ID
     */
    void deleteById(Long id);

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
     * 检查手机号是否存在
     *
     * @param phone 手机号
     * @return 是否存在
     */
    boolean existsByPhone(String phone);

    /**
     * 检查用户是否拥有指定权限
     *
     * @param username 用户名
     * @param permissionString 权限字符串，例如：user:read
     * @return 是否拥有权限
     */
    boolean hasPermission(String username, String permissionString);

    /**
     * 根据用户ID查询用户的角色和权限
     *
     * @param userId 用户ID
     * @return 角色和权限字符串列表，格式为：ROLE_角色名称 或 权限编码
     */
    List<String> findUserRolesAndPermissions(Long userId);
}