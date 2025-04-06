package com.example.backend_scaffold.application.dto.role;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 角色DTO
 * <p>
 * 用于在应用层和表示层之间传递角色信息
 * </p>
 *
 * @author example
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RoleDTO {

    /**
     * 角色ID
     */
    private Long id;

    /**
     * 角色名称
     */
    private String name;

    /**
     * 角色编码
     */
    private String code;

    /**
     * 角色描述
     */
    private String description;

    /**
     * 角色状态（0-禁用，1-启用）
     */
    private Integer status;

    /**
     * 角色排序
     */
    private Integer sort;

    /**
     * 权限ID列表
     */
    private List<Long> permissionIds;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;
}