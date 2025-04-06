package com.example.backend_scaffold.domain.model.entity;

import lombok.Getter;
import lombok.Setter;

/**
 * 角色实体类
 * <p>
 * 领域层的角色实体，不包含任何持久化相关的注解
 * </p>
 *
 * @author example
 */
@Getter
@Setter
public class RoleEntity extends BaseEntity {

    private Long id;

    /**
     * 角色名称
     */
    private String name;

    /**
     * 角色编码，唯一标识
     */
    private String code;

    /**
     * 角色描述
     */
    private String description;

    /**
     * 是否系统内置角色（内置角色不可删除）
     */
    private Boolean isSystem = false;

    /**
     * 是否启用
     */
    private Boolean isEnabled = true;

    /**
     * 排序号
     */
    private Integer sortOrder;
}