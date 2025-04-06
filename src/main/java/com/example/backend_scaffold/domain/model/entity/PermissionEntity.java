package com.example.backend_scaffold.domain.model.entity;

import com.example.backend_scaffold.domain.model.enums.PermissionType;
import lombok.Getter;
import lombok.Setter;

/**
 * 权限实体类
 * <p>
 * 领域层的权限实体，不包含任何持久化相关的注解
 * </p>
 *
 * @author example
 */
@Getter
@Setter
public class PermissionEntity extends BaseEntity {

    private Long id;

    /**
     * 权限名称
     */
    private String name;

    /**
     * 权限编码，唯一标识
     */
    private String code;

    /**
     * 权限类型
     */
    private PermissionType type;

    /**
     * 权限描述
     */
    private String description;

    /**
     * 权限对应的URL（当type为API时使用）
     */
    private String url;

    /**
     * 权限对应的方法（GET, POST, PUT, DELETE等，当type为API时使用）
     */
    private String method;

    /**
     * 菜单图标（当type为MENU时使用）
     */
    private String icon;

    /**
     * 排序号
     */
    private Integer sortOrder;

    /**
     * 父权限ID（用于构建权限树结构）
     */
    private Long parentId;

    /**
     * 是否启用
     */
    private Boolean isEnabled = true;
}