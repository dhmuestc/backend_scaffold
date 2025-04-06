package com.example.backend_scaffold.domain.model.aggregate;

import com.example.backend_scaffold.domain.model.entity.PermissionEntity;
import com.example.backend_scaffold.domain.model.entity.RoleEntity;
import com.example.backend_scaffold.domain.model.entity.RolePermissionEntity;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 角色聚合根
 * <p>
 * 角色聚合根包含角色的基本信息和关联的权限
 * </p>
 *
 * @author example
 */
@Getter
public class Role {

    /**
     * 角色ID
     */
    private final Long id;

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
    private Boolean isSystem;

    /**
     * 是否启用
     */
    private Boolean isEnabled;

    /**
     * 权限列表
     */
    private List<Permission> permissions;

    /**
     * 角色权限关联实体列表
     */
    private List<RolePermissionEntity> rolePermissionEntities;

    /**
     * 创建时间
     */
    private LocalDateTime createdAt;

    /**
     * 创建人
     */
    private String createdBy;

    /**
     * 更新时间
     */
    private LocalDateTime updatedAt;

    /**
     * 更新人
     */
    private String updatedBy;

    /**
     * 是否删除
     */
    private Boolean isDeleted;

    /**
     * 私有构造函数，从角色实体创建角色聚合根
     *
     * @param roleEntity 角色实体
     */
    private Role(RoleEntity roleEntity) {
        this.id = roleEntity.getId();
        this.name = roleEntity.getName();
        this.code = roleEntity.getCode();
        this.description = roleEntity.getDescription();
        this.isSystem = roleEntity.getIsSystem();
        this.isEnabled = roleEntity.getIsEnabled();
        this.createdAt = roleEntity.getCreatedAt();
        this.createdBy = roleEntity.getCreatedBy();
        this.updatedAt = roleEntity.getUpdatedAt();
        this.updatedBy = roleEntity.getUpdatedBy();
        this.isDeleted = roleEntity.getIsDeleted();
        this.permissions = new ArrayList<>();
        this.rolePermissionEntities = new ArrayList<>();
    }

    /**
     * 从角色实体创建角色聚合根
     *
     * @param roleEntity 角色实体
     * @return 角色聚合根
     */
    public static Role from(RoleEntity roleEntity) {
        return new Role(roleEntity);
    }

    /**
     * 从角色实体和权限列表创建角色聚合根
     *
     * @param roleEntity            角色实体
     * @param permissionEntities    权限实体列表
     * @param rolePermissionEntities 角色权限关联实体列表
     * @return 角色聚合根
     */
    public static Role from(RoleEntity roleEntity, List<PermissionEntity> permissionEntities, List<RolePermissionEntity> rolePermissionEntities) {
        Role role = new Role(roleEntity);
        role.setPermissions(permissionEntities);
        role.setRolePermissionEntities(rolePermissionEntities);
        return role;
    }

    /**
     * 设置权限列表
     *
     * @param permissionEntities 权限实体列表
     */
    private void setPermissions(List<PermissionEntity> permissionEntities) {
        if (permissionEntities != null && !permissionEntities.isEmpty()) {
            this.permissions = permissionEntities.stream()
                    .map(Permission::from)
                    .collect(Collectors.toList());
        }
    }

    /**
     * 设置角色权限关联实体列表
     *
     * @param rolePermissionEntities 角色权限关联实体列表
     */
    private void setRolePermissionEntities(List<RolePermissionEntity> rolePermissionEntities) {
        if (rolePermissionEntities != null) {
            this.rolePermissionEntities = new ArrayList<>(rolePermissionEntities);
        }
    }

    /**
     * 添加权限
     *
     * @param permission 权限
     */
    public void addPermission(Permission permission) {
        if (this.permissions == null) {
            this.permissions = new ArrayList<>();
        }
        this.permissions.add(permission);
    }

    /**
     * 移除权限
     *
     * @param permissionId 权限ID
     */
    public void removePermission(Long permissionId) {
        if (this.permissions != null) {
            this.permissions.removeIf(permission -> permission.getId().equals(permissionId));
        }
    }

    /**
     * 更新角色信息
     *
     * @param name        角色名称
     * @param code        角色编码
     * @param description 角色描述
     * @param isEnabled   是否启用
     */
    public void update(String name, String code, String description, Boolean isEnabled) {
        this.name = name;
        this.code = code;
        this.description = description;
        this.isEnabled = isEnabled;
    }

    /**
     * 启用角色
     */
    public void enable() {
        this.isEnabled = true;
    }

    /**
     * 禁用角色
     */
    public void disable() {
        this.isEnabled = false;
    }

    /**
     * 删除角色
     */
    public void delete() {
        this.isDeleted = true;
    }

    /**
     * 恢复角色
     */
    public void restore() {
        this.isDeleted = false;
    }

    /**
     * 转换为角色实体
     *
     * @return 角色实体
     */
    public RoleEntity toEntity() {
        RoleEntity roleEntity = new RoleEntity();
        roleEntity.setId(this.id);
        roleEntity.setName(this.name);
        roleEntity.setCode(this.code);
        roleEntity.setDescription(this.description);
        roleEntity.setIsSystem(this.isSystem);
        roleEntity.setIsEnabled(this.isEnabled);
        roleEntity.setCreatedAt(this.createdAt);
        roleEntity.setCreatedBy(this.createdBy);
        roleEntity.setUpdatedAt(this.updatedAt);
        roleEntity.setUpdatedBy(this.updatedBy);
        roleEntity.setIsDeleted(this.isDeleted);
        return roleEntity;
    }
}