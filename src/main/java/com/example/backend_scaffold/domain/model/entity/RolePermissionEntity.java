package com.example.backend_scaffold.domain.model.entity;

import lombok.Getter;
import lombok.Setter;

/**
 * 角色权限关联实体类
 * <p>
 * 用于建立角色和权限之间的多对多关系
 * </p>
 *
 * @author example
 */
@Getter
@Setter
public class RolePermissionEntity extends BaseEntity {

    private Long id;

    /**
     * 角色ID
     */
    private Long roleId;

    /**
     * 权限ID
     */
    private Long permissionId;

    /**
     * 是否启用
     */
    private Boolean isEnabled = true;
}