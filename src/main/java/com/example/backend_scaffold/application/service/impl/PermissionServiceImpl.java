package com.example.backend_scaffold.application.service.impl;

import com.example.backend_scaffold.application.dto.permission.PermissionRequest;
import com.example.backend_scaffold.application.dto.permission.PermissionResponse;
import com.example.backend_scaffold.application.exception.EntityNotFoundException;
import com.example.backend_scaffold.application.mapper.PermissionMapper;
import com.example.backend_scaffold.application.service.PermissionService;
import com.example.backend_scaffold.domain.model.entity.PermissionEntity;
import com.example.backend_scaffold.domain.model.enums.PermissionType;
import com.example.backend_scaffold.domain.service.PermissionDomainService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 权限服务实现类
 * <p>
 * 实现权限的创建、查询、更新、删除等功能
 * </p>
 *
 * @author example
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class PermissionServiceImpl implements PermissionService {

    private final PermissionDomainService permissionDomainService;
    private final PermissionMapper permissionMapper;

    @Override
    @Transactional
    public PermissionResponse createPermission(PermissionRequest permissionRequest) {
        log.info("Creating permission with code: {}", permissionRequest.getCode());
        PermissionEntity permissionEntity = permissionMapper.toEntity(permissionRequest);
        PermissionEntity savedEntity = permissionDomainService.createPermission(permissionEntity);
        return permissionMapper.entityToResponse(savedEntity);
    }

    @Override
    @Transactional
    public PermissionResponse updatePermission(Long id, PermissionRequest permissionRequest) {
        log.info("Updating permission with id: {}", id);
        PermissionEntity existingEntity = permissionDomainService.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Permission not found with id: " + id));
        
        PermissionEntity updatedEntity = permissionMapper.updateEntity(permissionRequest, existingEntity);
        PermissionEntity savedEntity = permissionDomainService.updatePermission(updatedEntity);
        return permissionMapper.entityToResponse(savedEntity);
    }

    @Override
    public PermissionResponse getPermissionById(Long id) {
        log.info("Getting permission by id: {}", id);
        PermissionEntity permissionEntity = permissionDomainService.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Permission not found with id: " + id));
        return permissionMapper.entityToResponse(permissionEntity);
    }

    @Override
    public PermissionResponse getPermission(Long id) {
        return getPermissionById(id);
    }

    @Override
    public PermissionResponse getPermissionByCode(String code) {
        log.info("Getting permission by code: {}", code);
        PermissionEntity permissionEntity = permissionDomainService.findByCode(code)
                .orElseThrow(() -> new EntityNotFoundException("Permission not found with code: " + code));
                return permissionMapper.entityToResponse(permissionEntity);
    }

    @Override
    public List<PermissionResponse> getAllPermissions() {
        log.info("Getting all permissions");
        List<PermissionEntity> permissionEntities = permissionDomainService.findAll();
        return permissionMapper.entitiesToResponseList(permissionEntities);
    }

    @Override
    public List<PermissionResponse> getPermissionsByType(PermissionType type) {
        log.info("Getting permissions by type: {}", type);
        List<PermissionEntity> permissionEntities = permissionDomainService.findByType(type);
        return permissionMapper.entitiesToResponseList(permissionEntities);
    }

    @Override
    public List<PermissionResponse> getPermissionsByParentId(Long parentId) {
        log.info("Getting permissions by parent id: {}", parentId);
        List<PermissionEntity> permissionEntities = permissionDomainService.findByParentId(parentId);
        return permissionMapper.entitiesToResponseList(permissionEntities);
    }

    @Override
    @Transactional
    public boolean deletePermission(Long id) {
        log.info("Deleting permission with id: {}", id);
        if (!permissionDomainService.findById(id).isPresent()) {
            log.warn("Permission not found with id: {}", id);
            return false;
        }
        permissionDomainService.deletePermission(id);
        return true;
    }

    @Override
    @Transactional
    public PermissionResponse togglePermissionStatus(Long id, boolean isEnabled) {
        log.info("Toggling permission status with id: {} to {}", id, isEnabled);
        boolean result;
        if (isEnabled) {
            result = permissionDomainService.enablePermission(id);
        } else {
            result = permissionDomainService.disablePermission(id);
        }
        
        if (!result) {
            throw new EntityNotFoundException("Permission not found with id: " + id);
        }
        
        PermissionEntity permissionEntity = permissionDomainService.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Permission not found with id: " + id));
        return permissionMapper.entityToResponse(permissionEntity);
    }

    @Override
    public Page<PermissionResponse> getPermissions(Pageable pageable) {
        log.info("Getting permissions with pagination: {}", pageable);
        List<PermissionEntity> permissionEntities = permissionDomainService.findAll();
        
        // 手动分页处理
        int start = (int) pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), permissionEntities.size());
        List<PermissionEntity> pageContent = permissionEntities.subList(start, end);
        
        List<PermissionResponse> permissionResponses = permissionMapper.entitiesToResponseList(pageContent);
        
        return new PageImpl<>(permissionResponses, pageable, permissionEntities.size());
    }
}