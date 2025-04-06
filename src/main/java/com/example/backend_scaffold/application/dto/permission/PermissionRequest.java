package com.example.backend_scaffold.application.dto.permission;

import com.example.backend_scaffold.domain.model.enums.PermissionType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 权限请求DTO
 * <p>
 * 用于创建或更新权限的请求数据传输对象
 * </p>
 *
 * @author example
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PermissionRequest {

    /**
     * 权限ID（更新时使用）
     */
    private Long id;

    /**
     * 权限名称
     */
    @NotBlank(message = "权限名称不能为空")
    @Size(max = 50, message = "权限名称长度不能超过50个字符")
    private String name;

    /**
     * 权限编码
     */
    @NotBlank(message = "权限编码不能为空")
    @Size(max = 100, message = "权限编码长度不能超过100个字符")
    private String code;

    /**
     * 权限类型
     */
    @NotNull(message = "权限类型不能为空")
    private PermissionType type;

    /**
     * 权限描述
     */
    @Size(max = 200, message = "权限描述长度不能超过200个字符")
    private String description;

    /**
     * 权限对应的URL（当type为API时使用）
     */
    @Size(max = 200, message = "URL长度不能超过200个字符")
    private String url;

    /**
     * 权限对应的方法（GET, POST, PUT, DELETE等，当type为API时使用）
     */
    @Size(max = 10, message = "方法长度不能超过10个字符")
    private String method;

    /**
     * 菜单图标（当type为MENU时使用）
     */
    @Size(max = 50, message = "图标长度不能超过50个字符")
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
    @Builder.Default
    private Boolean isEnabled = true;
}