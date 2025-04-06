package com.example.backend_scaffold.infrastructure.persistence.mapper;

import com.example.backend_scaffold.domain.model.entity.UserEntity;
import com.example.backend_scaffold.infrastructure.persistence.entity.UserJpaEntity;
import org.springframework.stereotype.Component;

/**
 * 用户JPA映射器
 * <p>
 * 负责在领域模型UserEntity和基础设施层UserJpaEntity之间进行转换
 * </p>
 *
 * @author example
 */
@Component
public class UserJpaMapper {

    /**
     * 将领域实体转换为JPA实体
     *
     * @param userEntity 领域实体
     * @return JPA实体
     */
    public UserJpaEntity toJpaEntity(UserEntity userEntity) {
        if (userEntity == null) {
            return null;
        }
        
        UserJpaEntity jpaEntity = new UserJpaEntity();
        jpaEntity.setId(userEntity.getId());
        jpaEntity.setUsername(userEntity.getUsername());
        jpaEntity.setPassword(userEntity.getPassword());
        jpaEntity.setNickname(userEntity.getNickname());
        jpaEntity.setRealName(userEntity.getRealName());
        jpaEntity.setAvatar(userEntity.getAvatar());
        jpaEntity.setEmail(userEntity.getEmail());
        jpaEntity.setPhone(userEntity.getPhone());
        jpaEntity.setCountryCode(userEntity.getCountryCode());
        jpaEntity.setStatus(userEntity.getStatus());
        jpaEntity.setLastLoginTime(userEntity.getLastLoginTime());
        jpaEntity.setLastLoginIp(userEntity.getLastLoginIp());
        
        // 地址信息
        jpaEntity.setCountry(userEntity.getCountry());
        jpaEntity.setProvince(userEntity.getProvince());
        jpaEntity.setCity(userEntity.getCity());
        jpaEntity.setDistrict(userEntity.getDistrict());
        jpaEntity.setAddressDetail(userEntity.getAddressDetail());
        jpaEntity.setZipCode(userEntity.getZipCode());
        
        // 审计信息
        jpaEntity.setCreatedAt(userEntity.getCreatedAt());
        jpaEntity.setCreatedBy(userEntity.getCreatedBy());
        jpaEntity.setUpdatedAt(userEntity.getUpdatedAt());
        jpaEntity.setUpdatedBy(userEntity.getUpdatedBy());
        jpaEntity.setVersion(userEntity.getVersion());
        jpaEntity.setIsDeleted(userEntity.getIsDeleted());
        
        return jpaEntity;
    }

    /**
     * 将JPA实体转换为领域实体
     *
     * @param jpaEntity JPA实体
     * @return 领域实体
     */
    public UserEntity toDomainEntity(UserJpaEntity jpaEntity) {
        if (jpaEntity == null) {
            return null;
        }
        
        UserEntity userEntity = new UserEntity();
        userEntity.setId(jpaEntity.getId());
        userEntity.setUsername(jpaEntity.getUsername());
        userEntity.setPassword(jpaEntity.getPassword());
        userEntity.setNickname(jpaEntity.getNickname());
        userEntity.setRealName(jpaEntity.getRealName());
        userEntity.setAvatar(jpaEntity.getAvatar());
        userEntity.setEmail(jpaEntity.getEmail());
        userEntity.setPhone(jpaEntity.getPhone());
        userEntity.setCountryCode(jpaEntity.getCountryCode());
        userEntity.setStatus(jpaEntity.getStatus());
        userEntity.setLastLoginTime(jpaEntity.getLastLoginTime());
        userEntity.setLastLoginIp(jpaEntity.getLastLoginIp());
        
        // 地址信息
        userEntity.setCountry(jpaEntity.getCountry());
        userEntity.setProvince(jpaEntity.getProvince());
        userEntity.setCity(jpaEntity.getCity());
        userEntity.setDistrict(jpaEntity.getDistrict());
        userEntity.setAddressDetail(jpaEntity.getAddressDetail());
        userEntity.setZipCode(jpaEntity.getZipCode());
        
        // 审计信息
        userEntity.setCreatedAt(jpaEntity.getCreatedAt());
        userEntity.setCreatedBy(jpaEntity.getCreatedBy());
        userEntity.setUpdatedAt(jpaEntity.getUpdatedAt());
        userEntity.setUpdatedBy(jpaEntity.getUpdatedBy());
        userEntity.setVersion(jpaEntity.getVersion());
        userEntity.setIsDeleted(jpaEntity.getIsDeleted());
        
        return userEntity;
    }
}