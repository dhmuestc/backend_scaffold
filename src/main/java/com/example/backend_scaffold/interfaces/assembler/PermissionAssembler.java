package com.example.backend_scaffold.interfaces.assembler;

import com.example.backend_scaffold.application.dto.permission.PermissionDTO;
import com.example.backend_scaffold.application.dto.permission.PermissionResponse;
import com.example.backend_scaffold.domain.model.aggregate.Permission;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 权限组装器，负责在领域模型和DTO之间进行转换
 */
@Component
public class PermissionAssembler {

    /**
     * 将权限聚合根转换为权限响应DTO
     *
     * @param permission 权限聚合根
     * @return 权限响应DTO
     */
    public PermissionResponse toResponse(Permission permission) {
        if (permission == null) {
            return null;
        }
        
        return PermissionResponse.builder()
                .id(permission.getId())
                .name(permission.getName())
                .code(permission.getCode())
                .type(permission.getType())
                .description(permission.getDescription())
                .createdAt(permission.getCreatedAt())
                .updatedAt(permission.getUpdatedAt())
                .build();
    }

    /**
     * 将权限聚合根列表转换为权限响应DTO列表
     *
     * @param permissions 权限聚合根列表
     * @return 权限响应DTO列表
     */
    public List<PermissionResponse> toResponseList(List<Permission> permissions) {
        if (permissions == null) {
            return null;
        }
        
        return permissions.stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    /**
     * 将权限聚合根转换为权限DTO
     *
     * @param permission 权限聚合根
     * @return 权限DTO
     */
    public PermissionDTO toDTO(Permission permission) {
        if (permission == null) {
            return null;
        }
        
        return PermissionDTO.builder()
                .id(permission.getId())
                .name(permission.getName())
                .code(permission.getCode())
                .type(permission.getType())
                .description(permission.getDescription())
                .build();
    }

    /**
     * 将权限聚合根列表转换为权限DTO列表
     *
     * @param permissions 权限聚合根列表
     * @return 权限DTO列表
     */
    public List<PermissionDTO> toDTOList(List<Permission> permissions) {
        if (permissions == null) {
            return null;
        }
        
        return permissions.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }
}