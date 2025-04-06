package com.example.backend_scaffold.domain.model.aggregate;

import com.example.backend_scaffold.domain.model.entity.PermissionEntity;
import com.example.backend_scaffold.domain.model.enums.PermissionType;
import lombok.Getter;

import java.time.LocalDateTime;

/**
 * 权限聚合根
 * <p>
 * 权限聚合根包含权限的基本信息
 * </p>
 *
 * @author example
 */
@Getter
public class Permission {

    /**
     * 权限ID
     */
    private final Long id;

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
     * 私有构造函数，从权限实体创建权限聚合根
     *
     * @param permissionEntity 权限实体
     */
    private Permission(PermissionEntity permissionEntity) {
        this.id = permissionEntity.getId();
        this.name = permissionEntity.getName();
        this.code = permissionEntity.getCode();
        this.type = permissionEntity.getType();
        this.description = permissionEntity.getDescription();
        this.createdAt = permissionEntity.getCreatedAt();
        this.createdBy = permissionEntity.getCreatedBy();
        this.updatedAt = permissionEntity.getUpdatedAt();
        this.updatedBy = permissionEntity.getUpdatedBy();
        this.isDeleted = permissionEntity.getIsDeleted();
    }

    /**
     * 从权限实体创建权限聚合根
     *
     * @param permissionEntity 权限实体
     * @return 权限聚合根
     */
    public static Permission from(PermissionEntity permissionEntity) {
        return new Permission(permissionEntity);
    }

    /**
     * 更新权限信息
     *
     * @param name        权限名称
     * @param code        权限编码
     * @param type        权限类型
     * @param description 权限描述
     */
    public void update(String name, String code, PermissionType type, String description) {
        this.name = name;
        this.code = code;
        this.type = type;
        this.description = description;
    }

    /**
     * 删除权限
     */
    public void delete() {
        this.isDeleted = true;
    }

    /**
     * 恢复权限
     */
    public void restore() {
        this.isDeleted = false;
    }

    /**
     * 转换为权限实体
     *
     * @return 权限实体
     */
    public PermissionEntity toEntity() {
        PermissionEntity permissionEntity = new PermissionEntity();
        permissionEntity.setId(this.id);
        permissionEntity.setName(this.name);
        permissionEntity.setCode(this.code);
        permissionEntity.setType(this.type);
        permissionEntity.setDescription(this.description);
        permissionEntity.setCreatedAt(this.createdAt);
        permissionEntity.setCreatedBy(this.createdBy);
        permissionEntity.setUpdatedAt(this.updatedAt);
        permissionEntity.setUpdatedBy(this.updatedBy);
        permissionEntity.setIsDeleted(this.isDeleted);
        return permissionEntity;
    }
}