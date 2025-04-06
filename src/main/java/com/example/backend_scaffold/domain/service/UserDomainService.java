package com.example.backend_scaffold.domain.service;

import com.example.backend_scaffold.domain.model.entity.UserEntity;
import com.example.backend_scaffold.domain.model.entity.UserRoleEntity;
import com.example.backend_scaffold.domain.model.enums.UserStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

/**
 * 用户领域服务接口
 * <p>
 * 定义用户相关的业务逻辑
 * </p>
 *
 * @author example
 */
public interface UserDomainService {

    /**
     * 创建用户
     *
     * @param userEntity 用户实体
     * @return 创建后的用户实体
     */
    UserEntity createUser(UserEntity userEntity);

    /**
     * 更新用户
     *
     * @param userEntity 用户实体
     * @return 更新后的用户实体
     */
    UserEntity updateUser(UserEntity userEntity);

    /**
     * 删除用户
     *
     * @param id 用户ID
     */
    void deleteUser(Long id);

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
     * 为用户分配角色
     *
     * @param userId 用户ID
     * @param roleId 角色ID
     * @return 用户角色关联实体
     */
    UserRoleEntity assignRole(Long userId, Long roleId);

    /**
     * 移除用户角色
     *
     * @param userId 用户ID
     * @param roleId 角色ID
     */
    void removeRole(Long userId, Long roleId);

    /**
     * 获取用户的所有角色ID
     *
     * @param userId 用户ID
     * @return 角色ID列表
     */
    List<Long> getUserRoleIds(Long userId);

    /**
     * 修改用户密码
     *
     * @param userId      用户ID
     * @param oldPassword 旧密码
     * @param newPassword 新密码
     * @return 是否修改成功
     */
    boolean changePassword(Long userId, String oldPassword, String newPassword);

    /**
     * 重置用户密码
     *
     * @param userId      用户ID
     * @param newPassword 新密码
     * @return 是否重置成功
     */
    boolean resetPassword(Long userId, String newPassword);

    /**
     * 锁定用户
     *
     * @param userId 用户ID
     * @return 是否锁定成功
     */
    boolean lockUser(Long userId);

    /**
     * 解锁用户
     *
     * @param userId 用户ID
     * @return 是否解锁成功
     */
    boolean unlockUser(Long userId);

    /**
     * 启用用户
     *
     * @param userId 用户ID
     * @return 是否启用成功
     */
    boolean enableUser(Long userId);

    /**
     * 禁用用户
     *
     * @param userId 用户ID
     * @return 是否禁用成功
     */
    boolean disableUser(Long userId);

    /**
     * 验证用户密码
     *
     * @param rawPassword     原始密码
     * @param encodedPassword 加密后的密码
     * @return 是否匹配
     */
    boolean verifyPassword(String rawPassword, String encodedPassword);

    /**
     * 加密密码
     *
     * @param rawPassword 原始密码
     * @return 加密后的密码
     */
    String encodePassword(String rawPassword);
}