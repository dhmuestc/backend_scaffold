package com.example.backend_scaffold.infrastructure.persistence.entity;

import com.example.backend_scaffold.domain.model.enums.PermissionType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

/**
 * 权限JPA实体类
 * <p>
 * 用于存储系统权限信息的JPA实体，负责与数据库交互
 * </p>
 *
 * @author example
 */
@Entity
@Table(name = "permissions")
@Getter
@Setter
public class PermissionJpaEntity extends BaseJpaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 权限名称
     */
    @Column(name = "name", nullable = false, length = 50)
    private String name;

    /**
     * 权限编码，唯一标识
     */
    @Column(name = "code", nullable = false, unique = true, length = 100)
    private String code;

    /**
     * 权限类型
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    private PermissionType type;

    /**
     * 权限描述
     */
    @Column(name = "description", length = 200)
    private String description;

    /**
     * 权限对应的URL（当type为API时使用）
     */
    @Column(name = "url", length = 200)
    private String url;

    /**
     * 权限对应的方法（GET, POST, PUT, DELETE等，当type为API时使用）
     */
    @Column(name = "method", length = 10)
    private String method;

    /**
     * 菜单图标（当type为MENU时使用）
     */
    @Column(name = "icon", length = 50)
    private String icon;

    /**
     * 排序号
     */
    @Column(name = "sort_order")
    private Integer sortOrder;

    /**
     * 父权限ID（用于构建权限树结构）
     */
    @Column(name = "parent_id")
    private Long parentId;

    /**
     * 是否启用
     */
    @Column(name = "is_enabled", nullable = false)
    private Boolean isEnabled = true;
}