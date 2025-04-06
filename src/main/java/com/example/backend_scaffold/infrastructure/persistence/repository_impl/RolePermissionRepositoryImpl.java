package com.example.backend_scaffold.infrastructure.persistence.repository_impl;

import com.example.backend_scaffold.domain.model.entity.RolePermissionEntity;
import com.example.backend_scaffold.domain.repository.RolePermissionRepository;
import com.example.backend_scaffold.infrastructure.persistence.entity.RolePermissionJpaEntity;
import com.example.backend_scaffold.infrastructure.persistence.mapper.RolePermissionJpaMapper;
import com.example.backend_scaffold.infrastructure.persistence.repository.RolePermissionJpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * 角色权限关联仓储实现类
 * <p>
 * 实现角色权限关联仓储接口，提供角色权限关联实体的存储和查询操作
 * </p>
 *
 * @author example
 */
@Repository
public class RolePermissionRepositoryImpl implements RolePermissionRepository {

    private final RolePermissionJpaRepository rolePermissionJpaRepository;
    private final RolePermissionJpaMapper rolePermissionJpaMapper;

    public RolePermissionRepositoryImpl(RolePermissionJpaRepository rolePermissionJpaRepository, 
                                        RolePermissionJpaMapper rolePermissionJpaMapper) {
        this.rolePermissionJpaRepository = rolePermissionJpaRepository;
        this.rolePermissionJpaMapper = rolePermissionJpaMapper;
    }

    @Override
    public RolePermissionEntity save(RolePermissionEntity rolePermissionEntity) {
        RolePermissionJpaEntity jpaEntity = rolePermissionJpaMapper.toJpaEntity(rolePermissionEntity);
        RolePermissionJpaEntity savedEntity = rolePermissionJpaRepository.save(jpaEntity);
        return rolePermissionJpaMapper.toDomainEntity(savedEntity);
    }

    @Override
    public Optional<RolePermissionEntity> findById(Long id) {
        return rolePermissionJpaRepository.findById(id)
                .map(rolePermissionJpaMapper::toDomainEntity);
    }

    @Override
    public List<RolePermissionEntity> findByRoleId(Long roleId) {
        return rolePermissionJpaRepository.findByRoleId(roleId).stream()
                .map(rolePermissionJpaMapper::toDomainEntity)
                .collect(Collectors.toList());
    }

    @Override
    public List<RolePermissionEntity> findByPermissionId(Long permissionId) {
        return rolePermissionJpaRepository.findByPermissionId(permissionId).stream()
                .map(rolePermissionJpaMapper::toDomainEntity)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<RolePermissionEntity> findByRoleIdAndPermissionId(Long roleId, Long permissionId) {
        return Optional.ofNullable(rolePermissionJpaRepository.findByRoleIdAndPermissionId(roleId, permissionId))
                .map(rolePermissionJpaMapper::toDomainEntity);
    }

    @Override
    public void deleteByRoleId(Long roleId) {
        rolePermissionJpaRepository.deleteByRoleId(roleId);
    }

    @Override
    public void deleteByPermissionId(Long permissionId) {
        rolePermissionJpaRepository.deleteByPermissionId(permissionId);
    }

    @Override
    public void deleteByRoleIdAndPermissionId(Long roleId, Long permissionId) {
        rolePermissionJpaRepository.deleteByRoleIdAndPermissionId(roleId, permissionId);
    }

    @Override
    public void deleteById(Long id) {
        rolePermissionJpaRepository.deleteById(id);
    }

    @Override
    public boolean existsByRoleIdAndPermissionId(Long roleId, Long permissionId) {
        return rolePermissionJpaRepository.existsByRoleIdAndPermissionId(roleId, permissionId);
    }
}