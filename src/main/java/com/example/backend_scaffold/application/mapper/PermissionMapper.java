package com.example.backend_scaffold.application.mapper;

import com.example.backend_scaffold.application.dto.permission.PermissionDTO;
import com.example.backend_scaffold.application.dto.permission.PermissionRequest;
import com.example.backend_scaffold.application.dto.permission.PermissionResponse;
import com.example.backend_scaffold.domain.model.entity.PermissionEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * 权限映射器
 * <p>
 * 用于在PermissionEntity和PermissionDTO之间进行转换
 * </p>
 *
 * @author example
 */
@Mapper(componentModel = "spring")
public interface PermissionMapper {

    PermissionMapper INSTANCE = Mappers.getMapper(PermissionMapper.class);

    /**
     * 将PermissionEntity转换为PermissionDTO
     *
     * @param entity 权限实体
     * @return 权限DTO
     */
    @Mapping(target = "status", expression = "java(entity.getIsEnabled() ? 1 : 0)")
    PermissionDTO toDto(PermissionEntity entity);

    /**
     * 将PermissionEntity列表转换为PermissionDTO列表
     *
     * @param entities 权限实体列表
     * @return 权限DTO列表
     */
    List<PermissionDTO> toDtoList(List<PermissionEntity> entities);

    /**
     * 将PermissionRequest转换为PermissionEntity
     *
     * @param request 权限请求DTO
     * @return 权限实体
     */
    @Mapping(target = "isEnabled", constant = "true")
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "updatedBy", ignore = true)
    PermissionEntity toEntity(PermissionRequest request);

    /**
     * 更新PermissionEntity
     *
     * @param request 权限请求DTO
     * @param entity  权限实体
     * @return 更新后的权限实体
     */
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "updatedBy", ignore = true)
    PermissionEntity updateEntity(PermissionRequest request, @MappingTarget PermissionEntity entity);

    /**
     * 将PermissionDTO转换为PermissionResponse
     *
     * @param dto 权限DTO
     * @return 权限响应DTO
     */
    PermissionResponse toResponse(PermissionDTO dto);

    /**
     * 将PermissionDTO列表转换为PermissionResponse列表
     *
     * @param dtos 权限DTO列表
     * @return 权限响应DTO列表
     */
    List<PermissionResponse> toResponseList(List<PermissionDTO> dtos);
    
    /**
     * 将PermissionEntity直接转换为PermissionResponse
     * <p>
     * 用于更新权限后直接返回响应，避免中间转换步骤
     * </p>
     *
     * @param entity 权限实体
     * @return 权限响应DTO
     */
    @Mapping(target = "isEnabled", source = "isEnabled")
    @Mapping(target = "createdAt", source = "createdAt")
    @Mapping(target = "updatedAt", source = "updatedAt")
    PermissionResponse entityToResponse(PermissionEntity entity);

    /**
     * 将PermissionEntity列表直接转换为PermissionResponse列表
     *
     * @param entities 权限DTO列表
     * @return 权限响应DTO列表
     */
    List<PermissionResponse> entitiesToResponseList(List<PermissionEntity> entities);
}