package com.example.backend_scaffold.domain.model.enums;

/**
 * 用户状态枚举
 * <p>
 * 用于表示用户的不同状态
 * </p>
 *
 * @author example
 */
public enum UserStatus {

    /**
     * 活跃状态，用户可以正常登录和使用系统
     */
    ACTIVE,

    /**
     * 锁定状态，用户暂时不能登录系统，但管理员可以解锁
     */
    LOCKED,

    /**
     * 禁用状态，用户被管理员禁止使用系统
     */
    DISABLED,

    /**
     * 未验证状态，用户注册后尚未验证邮箱或手机号
     */
    UNVERIFIED,

    /**
     * 已删除状态，用户账号已被删除
     */
    DELETED
}