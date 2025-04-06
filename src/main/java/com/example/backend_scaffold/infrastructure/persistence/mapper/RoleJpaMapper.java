package com.example.backend_scaffold.infrastructure.persistence.mapper;

import com.example.backend_scaffold.domain.model.entity.RoleEntity;
import com.example.backend_scaffold.infrastructure.persistence.entity.RoleJpaEntity;
import org.springframework.stereotype.Component;

/**
 * 角色JPA映射器
 * <p>
 * 负责在领域实体和JPA实体之间进行转换
 * </p>
 *
 * @author example
 */
@Component
public class RoleJpaMapper {

    /**
     * 将JPA实体转换为领域实体
     *
     * @param jpaEntity JPA实体
     * @return 领域实体
     */
    public RoleEntity toDomainEntity(RoleJpaEntity jpaEntity) {
        if (jpaEntity == null) {
            return null;
        }
        
        RoleEntity roleEntity = new RoleEntity();
        roleEntity.setId(jpaEntity.getId());
        roleEntity.setName(jpaEntity.getName());
        roleEntity.setCode(jpaEntity.getCode());
        roleEntity.setDescription(jpaEntity.getDescription());
        roleEntity.setIsSystem(jpaEntity.getIsSystem());
        roleEntity.setIsEnabled(jpaEntity.getIsEnabled());
        roleEntity.setSortOrder(jpaEntity.getSortOrder());
        
        // 复制基础字段
        roleEntity.setCreatedAt(jpaEntity.getCreatedAt());
        roleEntity.setCreatedBy(jpaEntity.getCreatedBy());
        roleEntity.setUpdatedAt(jpaEntity.getUpdatedAt());
        roleEntity.setUpdatedBy(jpaEntity.getUpdatedBy());
        roleEntity.setVersion(jpaEntity.getVersion());
        roleEntity.setIsDeleted(jpaEntity.getIsDeleted());
        
        return roleEntity;
    }

    /**
     * 将领域实体转换为JPA实体
     *
     * @param domainEntity 领域实体
     * @return JPA实体
     */
    public RoleJpaEntity toJpaEntity(RoleEntity domainEntity) {
        if (domainEntity == null) {
            return null;
        }
        
        RoleJpaEntity jpaEntity = new RoleJpaEntity();
        jpaEntity.setId(domainEntity.getId());
        jpaEntity.setName(domainEntity.getName());
        jpaEntity.setCode(domainEntity.getCode());
        jpaEntity.setDescription(domainEntity.getDescription());
        jpaEntity.setIsSystem(domainEntity.getIsSystem());
        jpaEntity.setIsEnabled(domainEntity.getIsEnabled());
        jpaEntity.setSortOrder(domainEntity.getSortOrder());
        
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