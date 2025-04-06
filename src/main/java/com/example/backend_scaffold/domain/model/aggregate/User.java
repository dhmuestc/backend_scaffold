package com.example.backend_scaffold.domain.model.aggregate;

import com.example.backend_scaffold.domain.model.entity.RoleEntity;
import com.example.backend_scaffold.domain.model.entity.UserEntity;
import com.example.backend_scaffold.domain.model.entity.UserRoleEntity;
import com.example.backend_scaffold.domain.model.enums.UserStatus;
import com.example.backend_scaffold.domain.model.valueobject.Address;
import com.example.backend_scaffold.domain.model.valueobject.Email;
import com.example.backend_scaffold.domain.model.valueobject.PhoneNumber;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 用户聚合根
 * <p>
 * 用户聚合根包含用户的基本信息和关联的角色
 * </p>
 *
 * @author example
 */
@Getter
public class User {

    /**
     * 用户ID
     */
    private final Long id;

    /**
     * 用户名
     */
    private String username;

    /**
     * 密码（加密存储）
     */
    private String password;

    /**
     * 昵称
     */
    private String nickname;

    /**
     * 真实姓名
     */
    private String realName;

    /**
     * 邮箱
     */
    private Email email;

    /**
     * 手机号
     */
    private PhoneNumber phone;

    /**
     * 地址
     */
    private Address address;

    /**
     * 头像URL
     */
    private String avatarUrl;

    /**
     * 用户状态
     */
    private UserStatus status;

    /**
     * 最后登录时间
     */
    private LocalDateTime lastLoginTime;

    /**
     * 最后登录IP
     */
    private String lastLoginIp;

    /**
     * 角色列表
     */
    private List<Role> roles;

    /**
     * 用户角色关联实体列表
     */
    private List<UserRoleEntity> userRoleEntities;

    /**
     * 创建时间
     */
    private LocalDateTime createdAt;

    /**
     * 创建人
     */
    private String createdBy;

    /**
     * 更新时间
     */
    private LocalDateTime updatedAt;

    /**
     * 更新人
     */
    private String updatedBy;

    /**
     * 是否删除
     */
    private Boolean isDeleted;

    /**
     * 私有构造函数，从用户实体创建用户聚合根
     *
     * @param userEntity 用户实体
     */
    private User(UserEntity userEntity) {
        this.id = userEntity.getId();
        this.username = userEntity.getUsername();
        this.password = userEntity.getPassword();
        this.nickname = userEntity.getNickname();
        this.realName = userEntity.getRealName();
        this.email = userEntity.getEmailObject();
        this.phone = userEntity.getPhoneObject();
        this.address = userEntity.getAddressObject();
        this.avatarUrl = userEntity.getAvatar();
        this.status = userEntity.getStatus();
        this.lastLoginTime = userEntity.getLastLoginTime();
        this.lastLoginIp = userEntity.getLastLoginIp();
        this.createdAt = userEntity.getCreatedAt();
        this.createdBy = userEntity.getCreatedBy();
        this.updatedAt = userEntity.getUpdatedAt();
        this.updatedBy = userEntity.getUpdatedBy();
        this.isDeleted = userEntity.getIsDeleted();
        this.roles = new ArrayList<>();
        this.userRoleEntities = new ArrayList<>();
    }

    /**
     * 从用户实体创建用户聚合根
     *
     * @param userEntity 用户实体
     * @return 用户聚合根
     */
    public static User from(UserEntity userEntity) {
        return new User(userEntity);
    }

    /**
     * 从用户实体和角色列表创建用户聚合根
     *
     * @param userEntity        用户实体
     * @param roleEntities      角色实体列表
     * @param userRoleEntities  用户角色关联实体列表
     * @return 用户聚合根
     */
    public static User from(UserEntity userEntity, List<RoleEntity> roleEntities, List<UserRoleEntity> userRoleEntities) {
        User user = new User(userEntity);
        user.setRoles(roleEntities);
        user.setUserRoleEntities(userRoleEntities);
        return user;
    }

    /**
     * 设置角色列表
     *
     * @param roleEntities 角色实体列表
     */
    private void setRoles(List<RoleEntity> roleEntities) {
        if (roleEntities != null && !roleEntities.isEmpty()) {
            this.roles = roleEntities.stream()
                    .map(Role::from)
                    .collect(Collectors.toList());
        }
    }

    /**
     * 设置用户角色关联实体列表
     *
     * @param userRoleEntities 用户角色关联实体列表
     */
    private void setUserRoleEntities(List<UserRoleEntity> userRoleEntities) {
        if (userRoleEntities != null) {
            this.userRoleEntities = new ArrayList<>(userRoleEntities);
        }
    }

    /**
     * 添加角色
     *
     * @param role 角色
     */
    public void addRole(Role role) {
        if (this.roles == null) {
            this.roles = new ArrayList<>();
        }
        this.roles.add(role);
    }

    /**
     * 移除角色
     *
     * @param roleId 角色ID
     */
    public void removeRole(Long roleId) {
        if (this.roles != null) {
            this.roles.removeIf(role -> role.getId().equals(roleId));
        }
    }

    /**
     * 更新用户基本信息
     *
     * @param nickname  昵称
     * @param realName  真实姓名
     * @param email     邮箱
     * @param phone     手机号
     * @param address   地址
     * @param avatarUrl 头像URL
     */
    public void updateBasicInfo(String nickname, String realName, Email email, PhoneNumber phone, Address address, String avatarUrl) {
        this.nickname = nickname;
        this.realName = realName;
        this.email = email;
        this.phone = phone;
        this.address = address;
        this.avatarUrl = avatarUrl;
    }

    /**
     * 更新密码
     *
     * @param password 新密码（已加密）
     */
    public void updatePassword(String password) {
        this.password = password;
    }

    /**
     * 更新用户状态
     *
     * @param status 用户状态
     */
    public void updateStatus(UserStatus status) {
        this.status = status;
    }

    /**
     * 记录登录信息
     *
     * @param loginTime 登录时间
     * @param loginIp   登录IP
     */
    public void recordLogin(LocalDateTime loginTime, String loginIp) {
        this.lastLoginTime = loginTime;
        this.lastLoginIp = loginIp;
    }

    /**
     * 锁定用户
     */
    public void lock() {
        this.status = UserStatus.LOCKED;
    }

    /**
     * 解锁用户
     */
    public void unlock() {
        this.status = UserStatus.ACTIVE;
    }

    /**
     * 禁用用户
     */
    public void disable() {
        this.status = UserStatus.DISABLED;
    }

    /**
     * 启用用户
     */
    public void enable() {
        this.status = UserStatus.ACTIVE;
    }

    /**
     * 获取用户全名
     * 
     * @return 用户全名
     */
    public String getFullName() {
        return this.realName;
    }

    /**
     * 获取用户手机号
     * 
     * @return 用户手机号
     */
    public PhoneNumber getPhoneNumber() {
        return this.phone;
    }

    /**
     * 删除用户
     */
    public void delete() {
        this.isDeleted = true;
        this.status = UserStatus.DELETED;
    }

    /**
     * 恢复用户
     */
    public void restore() {
        this.isDeleted = false;
        this.status = UserStatus.ACTIVE;
    }

    /**
     * 转换为用户实体
     *
     * @return 用户实体
     */
    public UserEntity toEntity() {
        UserEntity userEntity = new UserEntity();
        userEntity.setId(this.id);
        userEntity.setUsername(this.username);
        userEntity.setPassword(this.password);
        userEntity.setNickname(this.nickname);
        userEntity.setRealName(this.realName);
        userEntity.setEmailObject(this.email);
        userEntity.setPhoneObject(this.phone);
        userEntity.setAddressObject(this.address);
        userEntity.setAvatar(this.avatarUrl);
        userEntity.setStatus(this.status);
        userEntity.setLastLoginTime(this.lastLoginTime);
        userEntity.setLastLoginIp(this.lastLoginIp);
        userEntity.setCreatedAt(this.createdAt);
        userEntity.setCreatedBy(this.createdBy);
        userEntity.setUpdatedAt(this.updatedAt);
        userEntity.setUpdatedBy(this.updatedBy);
        userEntity.setIsDeleted(this.isDeleted);
        return userEntity;
    }
}