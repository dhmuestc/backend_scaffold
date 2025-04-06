package com.example.backend_scaffold.infrastructure.persistence.mapper;

import com.example.backend_scaffold.domain.model.entity.PermissionEntity;
import com.example.backend_scaffold.infrastructure.persistence.entity.PermissionJpaEntity;
import org.springframework.stereotype.Component;

/**
 * 权限JPA映射器
 * <p>
 * 负责在领域实体和JPA实体之间进行转换
 * </p>
 *
 * @author example
 */
@Component
public class PermissionJpaMapper {

    /**
     * 将JPA实体转换为领域实体
     *
     * @param jpaEntity JPA实体
     * @return 领域实体
     */
    public PermissionEntity toDomainEntity(PermissionJpaEntity jpaEntity) {
        if (jpaEntity == null) {
            return null;
        }
        
        PermissionEntity permissionEntity = new PermissionEntity();
        permissionEntity.setId(jpaEntity.getId());
        permissionEntity.setName(jpaEntity.getName());
        permissionEntity.setCode(jpaEntity.getCode());
        permissionEntity.setType(jpaEntity.getType());
        permissionEntity.setDescription(jpaEntity.getDescription());
        permissionEntity.setUrl(jpaEntity.getUrl());
        permissionEntity.setMethod(jpaEntity.getMethod());
        permissionEntity.setIcon(jpaEntity.getIcon());
        permissionEntity.setSortOrder(jpaEntity.getSortOrder());
        permissionEntity.setParentId(jpaEntity.getParentId());
        permissionEntity.setIsEnabled(jpaEntity.getIsEnabled());
        
        // 复制基础字段
        permissionEntity.setCreatedAt(jpaEntity.getCreatedAt());
        permissionEntity.setCreatedBy(jpaEntity.getCreatedBy());
        permissionEntity.setUpdatedAt(jpaEntity.getUpdatedAt());
        permissionEntity.setUpdatedBy(jpaEntity.getUpdatedBy());
        permissionEntity.setVersion(jpaEntity.getVersion());
        permissionEntity.setIsDeleted(jpaEntity.getIsDeleted());
        
        return permissionEntity;
    }

    /**
     * 将领域实体转换为JPA实体
     *
     * @param domainEntity 领域实体
     * @return JPA实体
     */
    public PermissionJpaEntity toJpaEntity(PermissionEntity domainEntity) {
        if (domainEntity == null) {
            return null;
        }
        
        PermissionJpaEntity jpaEntity = new PermissionJpaEntity();
        jpaEntity.setId(domainEntity.getId());
        jpaEntity.setName(domainEntity.getName());
        jpaEntity.setCode(domainEntity.getCode());
        jpaEntity.setType(domainEntity.getType());
        jpaEntity.setDescription(domainEntity.getDescription());
        jpaEntity.setUrl(domainEntity.getUrl());
        jpaEntity.setMethod(domainEntity.getMethod());
        jpaEntity.setIcon(domainEntity.getIcon());
        jpaEntity.setSortOrder(domainEntity.getSortOrder());
        jpaEntity.setParentId(domainEntity.getParentId());
        jpaEntity.setIsEnabled(domainEntity.getIsEnabled());
        
        // 复制基础字段
        jpaEntity.setCreatedAt(domainEntity.getCreatedAt());
        jpaEntity.setCreatedBy(domainEntity.getCreatedBy());
        jpaEntity.setUpdatedAt(domainEntity.getUpdatedAt());
        jpaEntity.setUpdatedBy(domainEntity.getUpdatedBy());
        jpaEntity.setVersion(domainEntity.getVersion());
        jpaEntity.setIsDeleted(domainEntity.getIsDeleted());
        
        return jpaEntity;
    }
}