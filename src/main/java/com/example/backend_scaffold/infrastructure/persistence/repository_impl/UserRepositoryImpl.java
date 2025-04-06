package com.example.backend_scaffold.infrastructure.persistence.repository_impl;

import com.example.backend_scaffold.domain.model.entity.PermissionEntity;
import com.example.backend_scaffold.domain.model.entity.UserEntity;
import com.example.backend_scaffold.domain.model.enums.UserStatus;
import com.example.backend_scaffold.domain.repository.UserRepository;
import com.example.backend_scaffold.infrastructure.persistence.entity.PermissionJpaEntity;
import com.example.backend_scaffold.infrastructure.persistence.entity.RoleJpaEntity;
import com.example.backend_scaffold.infrastructure.persistence.entity.UserJpaEntity;
import com.example.backend_scaffold.infrastructure.persistence.mapper.PermissionJpaMapper;
import com.example.backend_scaffold.infrastructure.persistence.mapper.UserJpaMapper;
import com.example.backend_scaffold.infrastructure.persistence.repository.PermissionJpaRepository;
import com.example.backend_scaffold.infrastructure.persistence.repository.UserJpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * 用户仓储实现类
 * <p>
 * 实现用户仓储接口，提供用户实体的存储和查询操作
 * </p>
 *
 * @author example
 */
@Repository
public class UserRepositoryImpl implements UserRepository {

    private final UserJpaRepository userJpaRepository;
    private final PermissionJpaRepository permissionJpaRepository;
    private final UserJpaMapper userJpaMapper;
    private final PermissionJpaMapper permissionJpaMapper;

    public UserRepositoryImpl(UserJpaRepository userJpaRepository, 
                             PermissionJpaRepository permissionJpaRepository,
                             UserJpaMapper userJpaMapper,
                             PermissionJpaMapper permissionJpaMapper) {
        this.userJpaRepository = userJpaRepository;
        this.permissionJpaRepository = permissionJpaRepository;
        this.userJpaMapper = userJpaMapper;
        this.permissionJpaMapper = permissionJpaMapper;
    }

    @Override
    public UserEntity save(UserEntity userEntity) {
        UserJpaEntity jpaEntity = userJpaMapper.toJpaEntity(userEntity);
        UserJpaEntity savedEntity = userJpaRepository.save(jpaEntity);
        return userJpaMapper.toDomainEntity(savedEntity);
    }

    @Override
    public Optional<UserEntity> findById(Long id) {
        return userJpaRepository.findById(id)
                .map(userJpaMapper::toDomainEntity);
    }

    @Override
    public Optional<UserEntity> findByUsername(String username) {
        return userJpaRepository.findByUsername(username)
                .map(userJpaMapper::toDomainEntity);
    }

    @Override
    public Optional<UserEntity> findByEmail(String email) {
        return userJpaRepository.findByEmail(email)
                .map(userJpaMapper::toDomainEntity);
    }

    @Override
    public Optional<UserEntity> findByPhone(String phone) {
        return userJpaRepository.findByPhone(phone)
                .map(userJpaMapper::toDomainEntity);
    }

    @Override
    public List<UserEntity> findByStatus(UserStatus status) {
        return userJpaRepository.findByStatus(status).stream()
                .map(userJpaMapper::toDomainEntity)
                .collect(Collectors.toList());
    }

    @Override
    public Page<UserEntity> findByStatus(UserStatus status, Pageable pageable) {
        return userJpaRepository.findByStatus(status, pageable)
                .map(userJpaMapper::toDomainEntity);
    }

    @Override
    public List<UserEntity> findAll() {
        return userJpaRepository.findAll().stream()
                .map(userJpaMapper::toDomainEntity)
                .collect(Collectors.toList());
    }

    @Override
    public Page<UserEntity> findAll(Pageable pageable) {
        return userJpaRepository.findAll(pageable)
                .map(userJpaMapper::toDomainEntity);
    }

    @Override
    public void deleteById(Long id) {
        userJpaRepository.deleteById(id);
    }

    @Override
    public boolean existsByUsername(String username) {
        return userJpaRepository.existsByUsername(username);
    }

    @Override
    public boolean existsByEmail(String email) {
        return userJpaRepository.existsByEmail(email);
    }

    @Override
    public boolean existsByPhone(String phone) {
        return userJpaRepository.existsByPhone(phone);
    }

    @Override
    public boolean hasPermission(String username, String permissionString) {
        // 首先根据用户名查找用户
        Optional<UserEntity> userOptional = findByUsername(username);
        if (!userOptional.isPresent()) {
            return false;
        }
        
        Long userId = userOptional.get().getId();
        
        // 查询用户的所有权限
        List<PermissionJpaEntity> jpaPermissions = permissionJpaRepository.findByUserId(userId);
        List<PermissionEntity> permissions = jpaPermissions.stream()
                .map(permissionJpaMapper::toDomainEntity)
                .collect(Collectors.toList());
        
        // 检查用户是否拥有指定权限
        return permissions.stream()
                .anyMatch(permission -> permissionString.equals(permission.getCode()));
    }

    @Override
    public List<String> findUserRolesAndPermissions(Long userId) {
        // 获取用户的所有权限
        List<PermissionJpaEntity> jpaPermissions = permissionJpaRepository.findByUserId(userId);
        List<PermissionEntity> permissions = jpaPermissions.stream()
                .map(permissionJpaMapper::toDomainEntity)
                .collect(Collectors.toList());
        
        // 将权限转换为字符串列表
        List<String> authorities = permissions.stream()
                .map(PermissionEntity::getCode)
                .collect(Collectors.toList());
        
        // 查询用户的所有角色
        List<RoleJpaEntity> roles = userJpaRepository.findRolesByUserId(userId);
        
        // 将角色转换为字符串列表，并添加ROLE_前缀
        List<String> roleAuthorities = roles.stream()
                .map(role -> "ROLE_" + role.getCode())
                .collect(java.util.stream.Collectors.toList());
        
        // 合并权限和角色
        authorities.addAll(roleAuthorities);
        
        return authorities;
    }
}