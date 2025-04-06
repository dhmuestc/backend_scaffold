package com.example.backend_scaffold.domain.model.entity;

import lombok.Getter;
import lombok.Setter;

/**
 * 用户角色关联实体类
 * <p>
 * 用于建立用户和角色之间的多对多关系
 * </p>
 *
 * @author example
 */
@Getter
@Setter
public class UserRoleEntity extends BaseEntity {

    private Long id;

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 角色ID
     */
    private Long roleId;

    /**
     * 是否启用
     */
    private Boolean isEnabled = true;
}