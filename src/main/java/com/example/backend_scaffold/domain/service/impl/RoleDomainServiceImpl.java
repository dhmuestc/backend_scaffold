package com.example.backend_scaffold.domain.service.impl;

import com.example.backend_scaffold.domain.model.entity.RoleEntity;
import com.example.backend_scaffold.domain.model.entity.RolePermissionEntity;
import com.example.backend_scaffold.domain.repository.RolePermissionRepository;
import com.example.backend_scaffold.domain.repository.RoleRepository;
import com.example.backend_scaffold.domain.service.RoleDomainService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * 角色领域服务实现类
 * <p>
 * 实现角色相关的业务逻辑
 * </p>
 *
 * @author example
 */
@Service
@RequiredArgsConstructor
public class RoleDomainServiceImpl implements RoleDomainService {

    private final RoleRepository roleRepository;
    private final RolePermissionRepository rolePermissionRepository;

    @Override
    @Transactional
    public RoleEntity createRole(RoleEntity roleEntity) {
        // 检查角色编码是否已存在
        if (roleRepository.findByCode(roleEntity.getCode()).isPresent()) {
            throw new RuntimeException("Role code already exists: " + roleEntity.getCode());
        }

        // 检查角色名称是否已存在
        if (roleRepository.findByName(roleEntity.getName()).isPresent()) {
            throw new RuntimeException("Role name already exists: " + roleEntity.getName());
        }

        // 设置默认启用状态
        if (roleEntity.getIsEnabled() == null) {
            roleEntity.setIsEnabled(true);
        }

        return roleRepository.save(roleEntity);
    }

    @Override
    @Transactional
    public RoleEntity updateRole(RoleEntity roleEntity) {
        // 查询原角色信息
        RoleEntity existingRole = roleRepository.findById(roleEntity.getId())
                .orElseThrow(() -> new RuntimeException("Role not found with id: " + roleEntity.getId()));

        // 检查角色编码是否已存在（排除自身）
        if (!existingRole.getCode().equals(roleEntity.getCode())
                && roleRepository.findByCode(roleEntity.getCode()).isPresent()) {
            throw new RuntimeException("Role code already exists: " + roleEntity.getCode());
        }

        // 检查角色名称是否已存在（排除自身）
        if (!existingRole.getName().equals(roleEntity.getName())
                && roleRepository.findByName(roleEntity.getName()).isPresent()) {
            throw new RuntimeException("Role name already exists: " + roleEntity.getName());
        }

        // 如果是系统内置角色，不允许修改编码
        if (Boolean.TRUE.equals(existingRole.getIsSystem()) && !existingRole.getCode().equals(roleEntity.getCode())) {
            throw new RuntimeException("Cannot change code for system role: " + existingRole.getCode());
        }

        return roleRepository.save(roleEntity);
    }

    @Override
    @Transactional
    public void deleteRole(Long id) {
        // 查询角色信息
        RoleEntity roleEntity = roleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Role not found with id: " + id));

        // 如果是系统内置角色，不允许删除
        if (Boolean.TRUE.equals(roleEntity.getIsSystem())) {
            throw new RuntimeException("Cannot delete system role: " + roleEntity.getCode());
        }

        // 删除角色权限关联
        rolePermissionRepository.deleteByRoleId(id);

        // 删除角色
        roleRepository.deleteById(id);
    }

    @Override
    public Optional<RoleEntity> findById(Long id) {
        return roleRepository.findById(id);
    }

    @Override
    public Optional<RoleEntity> findByCode(String code) {
        return roleRepository.findByCode(code);
    }

    @Override
    public Optional<RoleEntity> findByName(String name) {
        return roleRepository.findByName(name);
    }

    @Override
    public List<RoleEntity> findAll() {
        return roleRepository.findAll();
    }

    @Override
    public Page<RoleEntity> findAll(Pageable pageable) {
        return roleRepository.findAll(pageable);
    }

    @Override
    public List<RoleEntity> findByIsEnabled(Boolean isEnabled) {
        return roleRepository.findByIsEnabled(isEnabled);
    }

    @Override
    @Transactional
    public RolePermissionEntity assignPermission(Long roleId, Long permissionId) {
        // 检查角色是否存在
        if (!roleRepository.findById(roleId).isPresent()) {
            throw new RuntimeException("Role not found with id: " + roleId);
        }

        // 检查角色权限关联是否已存在
        Optional<RolePermissionEntity> existingRolePermission = rolePermissionRepository.findByRoleIdAndPermissionId(roleId, permissionId);
        if (existingRolePermission.isPresent()) {
            // 如果已存在但被禁用，则重新启用
            if (!existingRolePermission.get().getIsEnabled()) {
                RolePermissionEntity rolePermission = existingRolePermission.get();
                rolePermission.setIsEnabled(true);
                return rolePermissionRepository.save(rolePermission);
            }
            return existingRolePermission.get();
        }

        // 创建新的角色权限关联
        RolePermissionEntity rolePermission = new RolePermissionEntity();
        rolePermission.setRoleId(roleId);
        rolePermission.setPermissionId(permissionId);
        rolePermission.setIsEnabled(true);
        return rolePermissionRepository.save(rolePermission);
    }

    @Override
    @Transactional
    public void removePermission(Long roleId, Long permissionId) {
        rolePermissionRepository.deleteByRoleIdAndPermissionId(roleId, permissionId);
    }

    @Override
    public List<Long> getRolePermissionIds(Long roleId) {
        return rolePermissionRepository.findByRoleId(roleId).stream()
                .filter(RolePermissionEntity::getIsEnabled)
                .map(RolePermissionEntity::getPermissionId)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public boolean enableRole(Long roleId) {
        RoleEntity roleEntity = roleRepository.findById(roleId)
                .orElseThrow(() -> new RuntimeException("Role not found with id: " + roleId));
        
        if (Boolean.TRUE.equals(roleEntity.getIsEnabled())) {
            return true; // 已经是启用状态
        }
        
        roleEntity.setIsEnabled(true);
        roleRepository.save(roleEntity);
        return true;
    }

    @Override
    @Transactional
    public boolean disableRole(Long roleId) {
        RoleEntity roleEntity = roleRepository.findById(roleId)
                .orElseThrow(() -> new RuntimeException("Role not found with id: " + roleId));
        
        // 系统内置角色不允许禁用
        if (Boolean.TRUE.equals(roleEntity.getIsSystem())) {
            throw new RuntimeException("Cannot disable system role: " + roleEntity.getCode());
        }
        
        if (Boolean.FALSE.equals(roleEntity.getIsEnabled())) {
            return true; // 已经是禁用状态
        }
        
        roleEntity.setIsEnabled(false);
        roleRepository.save(roleEntity);
        return true;
    }
}