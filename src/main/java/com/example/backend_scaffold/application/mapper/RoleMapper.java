package com.example.backend_scaffold.application.mapper;

import com.example.backend_scaffold.application.dto.role.RoleDTO;
import com.example.backend_scaffold.application.dto.role.RoleRequest;
import com.example.backend_scaffold.application.dto.role.RoleResponse;
import com.example.backend_scaffold.domain.model.entity.RoleEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * 角色映射器
 * <p>
 * 用于在RoleEntity和RoleDTO之间进行转换
 * </p>
 *
 * @author example
 */
@Mapper(componentModel = "spring")
public interface RoleMapper {

    RoleMapper INSTANCE = Mappers.getMapper(RoleMapper.class);

    /**
     * 将RoleEntity转换为RoleDTO
     *
     * @param entity 角色实体
     * @return 角色DTO
     */
    @Mapping(target = "status", expression = "java(entity.getIsEnabled() ? 1 : 0)")
    @Mapping(target = "sort", source = "sortOrder")
    @Mapping(target = "permissionIds", ignore = true)
    @Mapping(target = "createTime", source = "createdAt")
    @Mapping(target = "updateTime", source = "updatedAt")
    RoleDTO toDto(RoleEntity entity);

    /**
     * 将RoleEntity列表转换为RoleDTO列表
     *
     * @param entities 角色实体列表
     * @return 角色DTO列表
     */
    List<RoleDTO> toDtoList(List<RoleEntity> entities);

    /**
     * 将RoleRequest转换为RoleEntity
     *
     * @param request 角色请求DTO
     * @return 角色实体
     */
    @Mapping(target = "isEnabled", source = "enabled")
    @Mapping(target = "sortOrder", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "updatedBy", ignore = true)
    RoleEntity toEntity(RoleRequest request);

    /**
     * 更新RoleEntity
     *
     * @param request 角色请求DTO
     * @param entity  角色实体
     * @return 更新后的角色实体
     */
    @Mapping(target = "isEnabled", source = "enabled")
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "updatedBy", ignore = true)
    RoleEntity updateEntity(RoleRequest request, @MappingTarget RoleEntity entity);

    /**
     * 将RoleDTO转换为RoleResponse
     *
     * @param dto 角色DTO
     * @return 角色响应DTO
     */
    @Mapping(target = "enabled", expression = "java(dto.getStatus() == 1)")
    @Mapping(target = "createdTime", source = "createTime")
    @Mapping(target = "updatedTime", source = "updateTime")
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "updatedBy", ignore = true)
    RoleResponse toResponse(RoleDTO dto);

    /**
     * 将RoleDTO列表转换为RoleResponse列表
     *
     * @param dtos 角色DTO列表
     * @return 角色响应DTO列表
     */
    List<RoleResponse> toResponseList(List<RoleDTO> dtos);
    
    /**
     * 将RoleEntity直接转换为RoleResponse
     * <p>
     * 用于更新角色后直接返回响应，避免中间转换步骤
     * </p>
     *
     * @param entity 角色实体
     * @return 角色响应DTO
     */
    @Mapping(target = "enabled", source = "isEnabled")
    @Mapping(target = "createdTime", source = "createdAt")
    @Mapping(target = "updatedTime", source = "updatedAt")
    @Mapping(target = "permissionIds", ignore = true)
    RoleResponse entityToResponse(RoleEntity entity);

    /**
     * 将RoleEntity列表直接转换为RoleResponse列表
     *
     * @param entities 角色RoleEntity列表
     * @return 角色响应RoleResponse列表
     */
    List<RoleResponse> entitesToResponseList(List<RoleEntity> entities);
}