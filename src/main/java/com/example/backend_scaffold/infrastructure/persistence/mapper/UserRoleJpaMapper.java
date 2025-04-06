package com.example.backend_scaffold.infrastructure.persistence.mapper;

import com.example.backend_scaffold.domain.model.entity.UserRoleEntity;
import com.example.backend_scaffold.infrastructure.persistence.entity.UserRoleJpaEntity;
import org.springframework.stereotype.Component;

/**
 * 用户角色关联JPA映射器
 * <p>
 * 负责在领域实体和JPA实体之间进行转换
 * </p>
 *
 * @author example
 */
@Component
public class UserRoleJpaMapper {

    /**
     * 将JPA实体转换为领域实体
     *
     * @param jpaEntity JPA实体
     * @return 领域实体
     */
    public UserRoleEntity toDomainEntity(UserRoleJpaEntity jpaEntity) {
        if (jpaEntity == null) {
            return null;
        }

        UserRoleEntity userRoleEntity = new UserRoleEntity();
        userRoleEntity.setId(jpaEntity.getId());
        userRoleEntity.setUserId(jpaEntity.getUserId());
        userRoleEntity.setRoleId(jpaEntity.getRoleId());
        userRoleEntity.setIsEnabled(jpaEntity.getIsEnabled());

        // 设置基础字段
        userRoleEntity.setCreatedAt(jpaEntity.getCreatedAt());
        userRoleEntity.setCreatedBy(jpaEntity.getCreatedBy());
        userRoleEntity.setUpdatedAt(jpaEntity.getUpdatedAt());
        userRoleEntity.setUpdatedBy(jpaEntity.getUpdatedBy());
        userRoleEntity.setVersion(jpaEntity.getVersion());
        userRoleEntity.setIsDeleted(jpaEntity.getIsDeleted());

        return userRoleEntity;
    }

    /**
     * 将领域实体转换为JPA实体
     *
     * @param domainEntity 领域实体
     * @return JPA实体
     */
    public UserRoleJpaEntity toJpaEntity(UserRoleEntity domainEntity) {
        if (domainEntity == null) {
            return null;
        }

        UserRoleJpaEntity jpaEntity = new UserRoleJpaEntity();
        jpaEntity.setId(domainEntity.getId());
        jpaEntity.setUserId(domainEntity.getUserId());
        jpaEntity.setRoleId(domainEntity.getRoleId());
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