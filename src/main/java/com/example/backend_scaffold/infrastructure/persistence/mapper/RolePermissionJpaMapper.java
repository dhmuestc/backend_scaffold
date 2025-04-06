package com.example.backend_scaffold.infrastructure.persistence.mapper;

import com.example.backend_scaffold.domain.model.entity.RolePermissionEntity;
import com.example.backend_scaffold.infrastructure.persistence.entity.RolePermissionJpaEntity;
import org.springframework.stereotype.Component;

/**
 * 角色权限关联JPA映射器
 * <p>
 * 负责在领域实体和JPA实体之间进行转换
 * </p>
 *
 * @author example
 */
@Component
public class RolePermissionJpaMapper {

    /**
     * 将JPA实体转换为领域实体
     *
     * @param jpaEntity JPA实体
     * @return 领域实体
     */
    public RolePermissionEntity toDomainEntity(RolePermissionJpaEntity jpaEntity) {
        if (jpaEntity == null) {
            return null;
        }

        RolePermissionEntity rolePermissionEntity = new RolePermissionEntity();
        rolePermissionEntity.setId(jpaEntity.getId());
        rolePermissionEntity.setRoleId(jpaEntity.getRoleId());
        rolePermissionEntity.setPermissionId(jpaEntity.getPermissionId());
        rolePermissionEntity.setIsEnabled(jpaEntity.getIsEnabled());

        // 设置基础字段
        rolePermissionEntity.setCreatedAt(jpaEntity.getCreatedAt());
        rolePermissionEntity.setCreatedBy(jpaEntity.getCreatedBy());
        rolePermissionEntity.setUpdatedAt(jpaEntity.getUpdatedAt());
        rolePermissionEntity.setUpdatedBy(jpaEntity.getUpdatedBy());
        rolePermissionEntity.setVersion(jpaEntity.getVersion());
        rolePermissionEntity.setIsDeleted(jpaEntity.getIsDeleted());

        return rolePermissionEntity;
    }

    /**
     * 将领域实体转换为JPA实体
     *
     * @param domainEntity 领域实体
     * @return JPA实体
     */
    public RolePermissionJpaEntity toJpaEntity(RolePermissionEntity domainEntity) {
        if (domainEntity == null) {
            return null;
        }

        RolePermissionJpaEntity jpaEntity = new RolePermissionJpaEntity();
        jpaEntity.setId(domainEntity.getId());
        jpaEntity.setRoleId(domainEntity.getRoleId());
        jpaEntity.setPermissionId(domainEntity.getPermissionId());
        jpaEntity.setIsEnabled(domainEntity.getIsEnabled());

        // 设置基础字段
        jpaEntity.setCreatedAt(domainEntity.getCreatedAt());
        jpaEntity.setCreatedBy(domainEntity.getCreatedBy());
        jpaEntity.setUpdatedAt(domainEntity.getUpdatedAt());
        jpaEntity.setUpdatedBy(domainEntity.getUpdatedBy());
        jpaEntity.setVersion(domainEntity.getVersion());
        jpaEntity.setIsDeleted(domainEntity.getIsDeleted());

        return jpaEntity;
    }
}