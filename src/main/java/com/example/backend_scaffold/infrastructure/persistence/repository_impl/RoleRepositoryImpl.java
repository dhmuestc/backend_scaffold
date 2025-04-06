package com.example.backend_scaffold.infrastructure.persistence.repository_impl;

import com.example.backend_scaffold.domain.model.entity.RoleEntity;
import com.example.backend_scaffold.domain.repository.RoleRepository;
import com.example.backend_scaffold.infrastructure.persistence.entity.RoleJpaEntity;
import com.example.backend_scaffold.infrastructure.persistence.mapper.RoleJpaMapper;
import com.example.backend_scaffold.infrastructure.persistence.repository.RoleJpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * 角色仓储实现类
 * <p>
 * 实现角色仓储接口，提供角色实体的存储和查询操作
 * </p>
 *
 * @author example
 */
@Repository
public class RoleRepositoryImpl implements RoleRepository {

    private final RoleJpaRepository roleJpaRepository;
    private final RoleJpaMapper roleJpaMapper;

    public RoleRepositoryImpl(RoleJpaRepository roleJpaRepository, RoleJpaMapper roleJpaMapper) {
        this.roleJpaRepository = roleJpaRepository;
        this.roleJpaMapper = roleJpaMapper;
    }

    @Override
    public RoleEntity save(RoleEntity roleEntity) {
        RoleJpaEntity jpaEntity = roleJpaMapper.toJpaEntity(roleEntity);
        RoleJpaEntity savedEntity = roleJpaRepository.save(jpaEntity);
        return roleJpaMapper.toDomainEntity(savedEntity);
    }

    @Override
    public Optional<RoleEntity> findById(Long id) {
        return roleJpaRepository.findById(id)
                .map(roleJpaMapper::toDomainEntity);
    }

    @Override
    public Optional<RoleEntity> findByCode(String code) {
        return roleJpaRepository.findByCode(code)
                .map(roleJpaMapper::toDomainEntity);
    }

    @Override
    public Optional<RoleEntity> findByName(String name) {
        return roleJpaRepository.findByName(name)
                .map(roleJpaMapper::toDomainEntity);
    }

    @Override
    public List<RoleEntity> findAll() {
        return roleJpaRepository.findAll().stream()
                .map(roleJpaMapper::toDomainEntity)
                .collect(Collectors.toList());
    }
    
    @Override
    public Page<RoleEntity> findAll(Pageable pageable) {
        return roleJpaRepository.findAll(pageable)
                .map(roleJpaMapper::toDomainEntity);
    }

    @Override
    public List<RoleEntity> findByIsEnabled(Boolean isEnabled) {
        return roleJpaRepository.findByIsEnabled(isEnabled).stream()
                .map(roleJpaMapper::toDomainEntity)
                .toList();
    }

    @Override
    public void deleteById(Long id) {
        roleJpaRepository.deleteById(id);
    }

    @Override
    public boolean existsByCode(String code) {
        return roleJpaRepository.existsByCode(code);
    }

    @Override
    public boolean existsByName(String name) {
        return roleJpaRepository.existsByName(name);
    }
}