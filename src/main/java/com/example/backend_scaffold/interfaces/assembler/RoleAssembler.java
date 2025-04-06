package com.example.backend_scaffold.interfaces.assembler;

import com.example.backend_scaffold.application.dto.role.RoleDTO;
import com.example.backend_scaffold.application.dto.role.RoleResponse;
import com.example.backend_scaffold.domain.model.aggregate.Role;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 角色组装器，负责在领域模型和DTO之间进行转换
 */
@Component
public class RoleAssembler {

    /**
     * 将角色聚合根转换为角色响应DTO
     *
     * @param role 角色聚合根
     * @return 角色响应DTO
     */
    public RoleResponse toResponse(Role role) {
        if (role == null) {
            return null;
        }
        
        return RoleResponse.builder()
                .id(role.getId())
                .name(role.getName())
                .code(role.getCode())
                .description(role.getDescription())
                .createdTime(role.getCreatedAt())
                .updatedTime(role.getUpdatedAt())
                .build();
    }

    /**
     * 将角色聚合根列表转换为角色响应DTO列表
     *
     * @param roles 角色聚合根列表
     * @return 角色响应DTO列表
     */
    public List<RoleResponse> toResponseList(List<Role> roles) {
        if (roles == null) {
            return null;
        }
        
        return roles.stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    /**
     * 将角色聚合根转换为角色DTO
     *
     * @param role 角色聚合根
     * @return 角色DTO
     */
    public RoleDTO toDTO(Role role) {
        if (role == null) {
            return null;
        }
        
        return RoleDTO.builder()
                .id(role.getId())
                .name(role.getName())
                .code(role.getCode())
                .description(role.getDescription())
                .build();
    }

    /**
     * 将角色聚合根列表转换为角色DTO列表
     *
     * @param roles 角色聚合根列表
     * @return 角色DTO列表
     */
    public List<RoleDTO> toDTOList(List<Role> roles) {
        if (roles == null) {
            return null;
        }
        
        return roles.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }
}