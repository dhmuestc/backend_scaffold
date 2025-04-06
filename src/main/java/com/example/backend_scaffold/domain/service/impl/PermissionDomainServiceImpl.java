package com.example.backend_scaffold.domain.service.impl;

import com.example.backend_scaffold.domain.model.entity.PermissionEntity;
import com.example.backend_scaffold.domain.model.enums.PermissionType;
import com.example.backend_scaffold.domain.repository.PermissionRepository;
import com.example.backend_scaffold.domain.repository.RolePermissionRepository;
import com.example.backend_scaffold.domain.service.PermissionDomainService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

/**
 * 权限领域服务实现类
 * <p>
 * 实现权限相关的业务逻辑
 * </p>
 *
 * @author example
 */
@Service
@RequiredArgsConstructor
public class PermissionDomainServiceImpl implements PermissionDomainService {

    private final PermissionRepository permissionRepository;
    private final RolePermissionRepository rolePermissionRepository;

    @Override
    @Transactional
    public PermissionEntity createPermission(PermissionEntity permissionEntity) {
        // 检查权限编码是否已存在
        if (permissionRepository.existsByCode(permissionEntity.getCode())) {
            throw new RuntimeException("Permission code already exists: " + permissionEntity.getCode());
        }

        // 检查权限名称是否已存在
        if (permissionRepository.existsByName(permissionEntity.getName())) {
            throw new RuntimeException("Permission name already exists: " + permissionEntity.getName());
        }

        // 设置默认启用状态
        if (permissionEntity.getIsEnabled() == null) {
            permissionEntity.setIsEnabled(true);
        }

        return permissionRepository.save(permissionEntity);
    }

    @Override
    @Transactional
    public PermissionEntity updatePermission(PermissionEntity permissionEntity) {
        // 查询原权限信息
        PermissionEntity existingPermission = permissionRepository.findById(permissionEntity.getId())
                .orElseThrow(() -> new RuntimeException("Permission not found with id: " + permissionEntity.getId()));

        // 检查权限编码是否已存在（排除自身）
        if (!existingPermission.getCode().equals(permissionEntity.getCode())
                && permissionRepository.existsByCode(permissionEntity.getCode())) {
            throw new RuntimeException("Permission code already exists: " + permissionEntity.getCode());
        }

        // 检查权限名称是否已存在（排除自身）
        if (!existingPermission.getName().equals(permissionEntity.getName())
                && permissionRepository.existsByName(permissionEntity.getName())) {
            throw new RuntimeException("Permission name already exists: " + permissionEntity.getName());
        }

        return permissionRepository.save(permissionEntity);
    }

    @Override
    @Transactional
    public void deletePermission(Long id) {
        // 检查权限是否存在
        if (!permissionRepository.findById(id).isPresent()) {
            throw new RuntimeException("Permission not found with id: " + id);
        }

        // 删除角色权限关联
        rolePermissionRepository.deleteByPermissionId(id);

        // 删除权限
        permissionRepository.deleteById(id);
    }

    @Override
    public Optional<PermissionEntity> findById(Long id) {
        return permissionRepository.findById(id);
    }

    @Override
    public Optional<PermissionEntity> findByCode(String code) {
        return permissionRepository.findByCode(code);
    }

    @Override
    public Optional<PermissionEntity> findByName(String name) {
        return permissionRepository.findByName(name);
    }

    @Override
    public List<PermissionEntity> findByType(PermissionType type) {
        return permissionRepository.findByType(type);
    }

    @Override
    public Optional<PermissionEntity> findByUrlAndMethod(String url, String method) {
        return permissionRepository.findByUrlAndMethod(url, method);
    }

    @Override
    public List<PermissionEntity> findByParentId(Long parentId) {
        return permissionRepository.findByParentId(parentId);
    }

    @Override
    public List<PermissionEntity> findAll() {
        return permissionRepository.findAll();
    }

    @Override
    public Page<PermissionEntity> findAll(Pageable pageable) {
        return permissionRepository.findAll(pageable);
    }

    @Override
    public List<PermissionEntity> findByIsEnabled(Boolean isEnabled) {
        return permissionRepository.findByIsEnabled(isEnabled);
    }

    @Override
    @Transactional
    public boolean enablePermission(Long id) {
        Optional<PermissionEntity> permissionOptional = permissionRepository.findById(id);
        if (!permissionOptional.isPresent()) {
            return false;
        }

        PermissionEntity permission = permissionOptional.get();
        if (!permission.getIsEnabled()) {
            permission.setIsEnabled(true);
            permissionRepository.save(permission);
            return true;
        }
        return false;
    }

    @Override
    @Transactional
    public boolean disablePermission(Long id) {
        Optional<PermissionEntity> permissionOptional = permissionRepository.findById(id);
        if (!permissionOptional.isPresent()) {
            return false;
        }

        PermissionEntity permission = permissionOptional.get();
        if (permission.getIsEnabled()) {
            permission.setIsEnabled(false);
            permissionRepository.save(permission);
            return true;
        }
        return false;
    }
}