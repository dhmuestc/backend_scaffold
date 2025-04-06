package com.example.backend_scaffold.application.dto.permission;

import com.example.backend_scaffold.domain.model.enums.PermissionType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 权限响应DTO
 * <p>
 * 用于向前端返回权限信息的数据传输对象
 * </p>
 *
 * @author example
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PermissionResponse {

    /**
     * 权限ID
     */
    private Long id;

    /**
     * 权限名称
     */
    private String name;

    /**
     * 权限编码
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
    private Boolean isEnabled;

    /**
     * 创建时间
     */
    private LocalDateTime createdAt;

    /**
     * 更新时间
     */
    private LocalDateTime updatedAt;

    /**
     * 创建人
     */
    private String createdBy;

    /**
     * 更新人
     */
    private String updatedBy;

    /**
     * 子权限列表（用于构建权限树结构）
     */
    private List<PermissionResponse> children;
}