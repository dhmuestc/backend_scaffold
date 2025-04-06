package com.example.backend_scaffold.infrastructure.cache;

/**
 * 缓存键常量类
 * <p>
 * 定义系统中使用的所有缓存键，避免硬编码和重复定义
 * </p>
 *
 * @author example
 */
public class CacheKey {

    /**
     * 用户缓存前缀
     */
    public static final String USER_PREFIX = "user:";

    /**
     * 用户ID缓存键
     */
    public static final String USER_ID = USER_PREFIX + "id:";

    /**
     * 用户名缓存键
     */
    public static final String USER_NAME = USER_PREFIX + "name:";

    /**
     * 用户邮箱缓存键
     */
    public static final String USER_EMAIL = USER_PREFIX + "email:";

    /**
     * 用户手机号缓存键
     */
    public static final String USER_PHONE = USER_PREFIX + "phone:";

    /**
     * 用户角色缓存键
     */
    public static final String USER_ROLES = USER_PREFIX + "roles:";

    /**
     * 角色缓存前缀
     */
    public static final String ROLE_PREFIX = "role:";

    /**
     * 角色ID缓存键
     */
    public static final String ROLE_ID = ROLE_PREFIX + "id:";

    /**
     * 角色名称缓存键
     */
    public static final String ROLE_NAME = ROLE_PREFIX + "name:";

    /**
     * 角色权限缓存键
     */
    public static final String ROLE_PERMISSIONS = ROLE_PREFIX + "permissions:";

    /**
     * 权限缓存前缀
     */
    public static final String PERMISSION_PREFIX = "permission:";

    /**
     * 权限ID缓存键
     */
    public static final String PERMISSION_ID = PERMISSION_PREFIX + "id:";

    /**
     * 权限名称缓存键
     */
    public static final String PERMISSION_NAME = PERMISSION_PREFIX + "name:";

    /**
     * 权限编码缓存键
     */
    public static final String PERMISSION_CODE = PERMISSION_PREFIX + "code:";

    /**
     * 验证码缓存键
     */
    public static final String CAPTCHA_CODE = "captcha:code:";

    /**
     * 令牌黑名单缓存键
     */
    public static final String TOKEN_BLACKLIST = "token:blacklist:";

    /**
     * 系统配置缓存键
     */
    public static final String SYSTEM_CONFIG = "system:config";

    /**
     * 私有构造函数，防止实例化
     */
    private CacheKey() {
        throw new IllegalStateException("Utility class");
    }
}