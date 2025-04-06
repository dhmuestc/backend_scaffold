package com.example.backend_scaffold.infrastructure.persistence.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

/**
 * 角色JPA实体类
 * <p>
 * 用于存储系统角色信息的JPA实体，负责与数据库交互
 * </p>
 *
 * @author example
 */
@Entity
@Table(name = "roles")
@Getter
@Setter
public class RoleJpaEntity extends BaseJpaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 角色名称
     */
    @Column(name = "name", nullable = false, length = 50)
    private String name;

    /**
     * 角色编码，唯一标识
     */
    @Column(name = "code", nullable = false, unique = true, length = 100)
    private String code;

    /**
     * 角色描述
     */
    @Column(name = "description", length = 200)
    private String description;

    /**
     * 是否系统内置角色（内置角色不可删除）
     */
    @Column(name = "is_system", nullable = false)
    private Boolean isSystem = false;

    /**
     * 是否启用
     */
    @Column(name = "is_enabled", nullable = false)
    private Boolean isEnabled = true;

    /**
     * 排序号
     */
    @Column(name = "sort_order")
    private Integer sortOrder;
}