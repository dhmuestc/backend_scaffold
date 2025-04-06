package com.example.backend_scaffold.application.service.impl;

import com.example.backend_scaffold.application.dto.role.RolePermissionRequest;
import com.example.backend_scaffold.application.dto.role.RoleRequest;
import com.example.backend_scaffold.application.dto.role.RoleResponse;
import com.example.backend_scaffold.application.exception.BusinessException;
import com.example.backend_scaffold.application.exception.EntityNotFoundException;
import com.example.backend_scaffold.application.mapper.RoleMapper;
import com.example.backend_scaffold.application.service.RoleService;
import com.example.backend_scaffold.domain.model.entity.RoleEntity;
import com.example.backend_scaffold.domain.service.RoleDomainService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 角色服务实现类
 * <p>
 * 实现角色相关的业务逻辑
 * </p>
 *
 * @author example
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class RoleServiceImpl implements RoleService {

    private final RoleDomainService roleDomainService;
    private final RoleMapper roleMapper;
    private final com.example.backend_scaffold.domain.repository.RoleRepository roleRepository;

    @Override
    @Transactional
    public RoleResponse createRole(RoleRequest roleRequest) {
        try {
            RoleEntity roleEntity = roleMapper.toEntity(roleRequest);
            RoleEntity savedEntity = roleDomainService.createRole(roleEntity);
            return roleMapper.entityToResponse(savedEntity);
        } catch (RuntimeException e) {
            log.error("Failed to create role: {}", e.getMessage());
            throw new BusinessException("Failed to create role: " + e.getMessage());
        }
    }

    @Override
    @Transactional
    @CacheEvict(value = "roles", key = "#id")
    public RoleResponse updateRole(Long id, RoleRequest roleRequest) {
        try {
            RoleEntity existingRole = roleDomainService.findById(id)
                    .orElseThrow(() -> new EntityNotFoundException("Role not found with id: " + id));

            RoleEntity updatedEntity = roleMapper.updateEntity(roleRequest, existingRole);
            RoleEntity savedEntity = roleDomainService.updateRole(updatedEntity);
            return roleMapper.entityToResponse(savedEntity);
        } catch (EntityNotFoundException e) {
            throw e;
        } catch (RuntimeException e) {
            log.error("Failed to update role: {}", e.getMessage());
            throw new BusinessException("Failed to update role: " + e.getMessage());
        }
    }

    @Override
    @Cacheable(value = "roles", key = "#id")
    public RoleResponse getRoleById(Long id) {
        RoleEntity roleEntity = roleDomainService.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Role not found with id: " + id));
        return roleMapper.entityToResponse(roleEntity);
    }

    @Override
    @Cacheable(value = "roles", key = "#code")
    public RoleResponse getRoleByCode(String code) {
        RoleEntity roleEntity = roleDomainService.findByCode(code)
                .orElseThrow(() -> new EntityNotFoundException("Role not found with code: " + code));
        return roleMapper.entityToResponse(roleEntity);
    }

    @Override
    @Cacheable(value = "allRoles")
    public List<RoleResponse> getAllRoles() {
        List<RoleEntity> roleEntities = roleDomainService.findAll();
        return roleMapper.entitesToResponseList(roleEntities);
    }

    @Override
    @Transactional
    @CacheEvict(value = {"roles", "allRoles", "rolePermissions"}, allEntries = true)
    public void deleteRole(Long id) {
        try {
            roleDomainService.deleteRole(id);
        } catch (RuntimeException e) {
            log.error("Failed to delete role: {}", e.getMessage());
            throw new BusinessException("Failed to delete role: " + e.getMessage());
        }
    }

    @Override
    @Transactional
    @CacheEvict(value = {"roles", "allRoles", "rolePermissions"}, allEntries = true)
    public RoleResponse assignPermissionsToRole(Long roleId, RolePermissionRequest rolePermissionRequest) {
        try {
            // 验证角色是否存在
            RoleEntity roleEntity = roleDomainService.findById(roleId)
                    .orElseThrow(() -> new EntityNotFoundException("Role not found with id: " + roleId));

            // 先清除原有权限
            List<Long> existingPermissions = roleDomainService.getRolePermissionIds(roleId);
            for (Long permissionId : existingPermissions) {
                roleDomainService.removePermission(roleId, permissionId);
            }

            // 分配新权限
            for (Long permissionId : rolePermissionRequest.getPermissionIds()) {
                roleDomainService.assignPermission(roleId, permissionId);
            }

            // 返回更新后的角色信息
            RoleResponse roleResponse = roleMapper.entityToResponse(roleEntity);
            roleResponse.setPermissionIds(rolePermissionRequest.getPermissionIds());
            return roleResponse;
        } catch (EntityNotFoundException e) {
            throw e;
        } catch (RuntimeException e) {
            log.error("Failed to assign permissions to role: {}", e.getMessage());
            throw new BusinessException("Failed to assign permissions to role: " + e.getMessage());
        }
    }

    @Override
    @Cacheable(value = "rolePermissions", key = "#roleId")
    public List<Long> getRolePermissions(Long roleId) {
        // 验证角色是否存在
        roleDomainService.findById(roleId)
                .orElseThrow(() -> new EntityNotFoundException("Role not found with id: " + roleId));

        return roleDomainService.getRolePermissionIds(roleId);
    }

    @Override
    @Cacheable(value = "rolesPaged", key = "#pageable.pageNumber + '-' + #pageable.pageSize")
    public Page<RoleResponse> getRoles(Pageable pageable) {
        Page<RoleEntity> rolePage = roleRepository.findAll(pageable);
        return rolePage.map(entity -> {
            return roleMapper.entityToResponse(entity);
        });
    }
}