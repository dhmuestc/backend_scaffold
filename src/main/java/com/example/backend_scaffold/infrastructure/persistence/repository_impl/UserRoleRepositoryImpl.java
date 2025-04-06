package com.example.backend_scaffold.infrastructure.persistence.repository_impl;

import com.example.backend_scaffold.domain.model.entity.UserRoleEntity;
import com.example.backend_scaffold.domain.repository.UserRoleRepository;
import com.example.backend_scaffold.infrastructure.persistence.entity.UserRoleJpaEntity;
import com.example.backend_scaffold.infrastructure.persistence.mapper.UserRoleJpaMapper;
import com.example.backend_scaffold.infrastructure.persistence.repository.UserRoleJpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * 用户角色关联仓储实现类
 * <p>
 * 实现用户角色关联仓储接口，提供用户角色关联实体的存储和查询操作
 * </p>
 *
 * @author example
 */
@Repository
public class UserRoleRepositoryImpl implements UserRoleRepository {

    private final UserRoleJpaRepository userRoleJpaRepository;
    private final UserRoleJpaMapper userRoleJpaMapper;

    public UserRoleRepositoryImpl(UserRoleJpaRepository userRoleJpaRepository, UserRoleJpaMapper userRoleJpaMapper) {
        this.userRoleJpaRepository = userRoleJpaRepository;
        this.userRoleJpaMapper = userRoleJpaMapper;
    }

    @Override
    public UserRoleEntity save(UserRoleEntity userRoleEntity) {
        UserRoleJpaEntity jpaEntity = userRoleJpaMapper.toJpaEntity(userRoleEntity);
        UserRoleJpaEntity savedEntity = userRoleJpaRepository.save(jpaEntity);
        return userRoleJpaMapper.toDomainEntity(savedEntity);
    }

    @Override
    public Optional<UserRoleEntity> findById(Long id) {
        return userRoleJpaRepository.findById(id)
                .map(userRoleJpaMapper::toDomainEntity);
    }

    @Override
    public List<UserRoleEntity> findByUserId(Long userId) {
        return userRoleJpaRepository.findByUserId(userId).stream()
                .map(userRoleJpaMapper::toDomainEntity)
                .collect(Collectors.toList());
    }

    @Override
    public List<UserRoleEntity> findByRoleId(Long roleId) {
        return userRoleJpaRepository.findByRoleId(roleId).stream()
                .map(userRoleJpaMapper::toDomainEntity)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<UserRoleEntity> findByUserIdAndRoleId(Long userId, Long roleId) {
        return userRoleJpaRepository.findByUserIdAndRoleId(userId, roleId)
                .map(userRoleJpaMapper::toDomainEntity);
    }

    @Override
    public void deleteByUserId(Long userId) {
        userRoleJpaRepository.deleteByUserId(userId);
    }

    @Override
    public void deleteByRoleId(Long roleId) {
        userRoleJpaRepository.deleteByRoleId(roleId);
    }

    @Override
    public void deleteByUserIdAndRoleId(Long userId, Long roleId) {
        userRoleJpaRepository.deleteByUserIdAndRoleId(userId, roleId);
    }

    @Override
    public void deleteById(Long id) {
        userRoleJpaRepository.deleteById(id);
    }

    @Override
    public boolean existsByUserIdAndRoleId(Long userId, Long roleId) {
        return userRoleJpaRepository.findByUserIdAndRoleId(userId, roleId).isPresent();
    }
}