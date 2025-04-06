package com.example.backend_scaffold.infrastructure.persistence.repository_impl;

import com.example.backend_scaffold.domain.model.entity.PermissionEntity;
import com.example.backend_scaffold.domain.model.enums.PermissionType;
import com.example.backend_scaffold.domain.repository.PermissionRepository;
import com.example.backend_scaffold.infrastructure.persistence.entity.PermissionJpaEntity;
import com.example.backend_scaffold.infrastructure.persistence.mapper.PermissionJpaMapper;
import com.example.backend_scaffold.infrastructure.persistence.repository.PermissionJpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * 权限仓储实现类
 * <p>
 * 实现权限仓储接口，提供权限实体的存储和查询操作
 * </p>
 *
 * @author example
 */
@Repository
public class PermissionRepositoryImpl implements PermissionRepository {

    private final PermissionJpaRepository permissionJpaRepository;
    private final PermissionJpaMapper permissionJpaMapper;

    public PermissionRepositoryImpl(PermissionJpaRepository permissionJpaRepository, PermissionJpaMapper permissionJpaMapper) {
        this.permissionJpaRepository = permissionJpaRepository;
        this.permissionJpaMapper = permissionJpaMapper;
    }

    @Override
    public PermissionEntity save(PermissionEntity permissionEntity) {
        PermissionJpaEntity jpaEntity = permissionJpaMapper.toJpaEntity(permissionEntity);
        PermissionJpaEntity savedJpaEntity = permissionJpaRepository.save(jpaEntity);
        return permissionJpaMapper.toDomainEntity(savedJpaEntity);
    }

    @Override
    public Optional<PermissionEntity> findById(Long id) {
        return permissionJpaRepository.findById(id)
                .map(permissionJpaMapper::toDomainEntity);
    }

    @Override
    public Optional<PermissionEntity> findByCode(String code) {
        return permissionJpaRepository.findByCode(code)
                .map(permissionJpaMapper::toDomainEntity);
    }

    @Override
    public Optional<PermissionEntity> findByName(String name) {
        return permissionJpaRepository.findByName(name)
                .map(permissionJpaMapper::toDomainEntity);
    }

    @Override
    public List<PermissionEntity> findAll() {
        return permissionJpaRepository.findAll().stream()
                .map(permissionJpaMapper::toDomainEntity)
                .collect(Collectors.toList());
    }

    @Override
    public Page<PermissionEntity> findAll(Pageable pageable) {
        return permissionJpaRepository.findAll(pageable)
                .map(permissionJpaMapper::toDomainEntity);
    }

    @Override
    public List<PermissionEntity> findByType(PermissionType type) {
        return permissionJpaRepository.findByType(type).stream()
                .map(permissionJpaMapper::toDomainEntity)
                .collect(Collectors.toList());
    }

    @Override
    public List<PermissionEntity> findByParentId(Long parentId) {
        return permissionJpaRepository.findByParentId(parentId).stream()
                .map(permissionJpaMapper::toDomainEntity)
                .collect(Collectors.toList());
    }

    @Override
    public List<PermissionEntity> findByUrl(String url) {
        return permissionJpaRepository.findByUrl(url).stream()
                .map(permissionJpaMapper::toDomainEntity)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<PermissionEntity> findByUrlAndMethod(String url, String method) {
        return permissionJpaRepository.findByUrlAndMethod(url, method)
                .map(permissionJpaMapper::toDomainEntity);
    }

    @Override
    public void deleteById(Long id) {
        permissionJpaRepository.deleteById(id);
    }

    @Override
    public boolean existsByCode(String code) {
        return permissionJpaRepository.findByCode(code).isPresent();
    }

    @Override
    public boolean existsByName(String name) {
        return permissionJpaRepository.existsByName(name);
    }

    @Override
    public List<PermissionEntity> findByIsEnabled(Boolean isEnabled) {
        if (isEnabled != null && isEnabled) {
            // 使用JpaRepository提供的findByIsEnabledTrue方法
            return permissionJpaRepository.findByIsEnabledTrue().stream()
                    .map(permissionJpaMapper::toDomainEntity)
                    .collect(Collectors.toList());
        } else {
            // 手动过滤未启用的权限
            return permissionJpaRepository.findAll().stream()
                    .filter(permission -> isEnabled == null || permission.getIsEnabled().equals(isEnabled))
                    .map(permissionJpaMapper::toDomainEntity)
                    .collect(Collectors.toList());
        }
    }
}