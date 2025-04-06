package com.example.backend_scaffold.application.mapper;

import com.example.backend_scaffold.application.dto.user.UserDTO;
import com.example.backend_scaffold.application.dto.user.UserRequest;
import com.example.backend_scaffold.application.dto.user.UserResponse;
import com.example.backend_scaffold.domain.model.entity.UserEntity;
import com.example.backend_scaffold.domain.model.valueobject.Address;
import com.example.backend_scaffold.domain.model.valueobject.Email;
import com.example.backend_scaffold.domain.model.valueobject.PhoneNumber;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * 用户映射器
 * <p>
 * 用于在UserEntity和UserDTO之间进行转换
 * </p>
 *
 * @author example
 */
@Mapper(componentModel = "spring")
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    /**
     * 将UserEntity转换为UserDTO
     *
     * @param entity 用户实体
     * @return 用户DTO
     */
    @Mapping(target = "createdTime", source = "createdAt")
    @Mapping(target = "updatedTime", source = "updatedAt")
    @Mapping(target = "roleIds", ignore = true)
    UserDTO toDto(UserEntity entity);

    /**
     * 将UserEntity列表转换为UserDTO列表
     *
     * @param entities 用户实体列表
     * @return 用户DTO列表
     */
    List<UserDTO> toDtoList(List<UserEntity> entities);

    /**
     * 将UserRequest转换为UserEntity
     *
     * @param request 用户请求DTO
     * @return 用户实体
     */
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "status", constant = "ACTIVE")
    @Mapping(target = "lastLoginTime", ignore = true)
    @Mapping(target = "lastLoginIp", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "updatedBy", ignore = true)
    UserEntity toEntity(UserRequest request);

    /**
     * 更新UserEntity
     *
     * @param request 用户请求DTO
     * @param entity  用户实体
     * @return 更新后的用户实体
     */
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "password", ignore = true) // 密码更新应该通过专门的接口
    @Mapping(target = "lastLoginTime", ignore = true)
    @Mapping(target = "lastLoginIp", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "updatedBy", ignore = true)
    UserEntity updateEntity(UserRequest request, @MappingTarget UserEntity entity);

    /**
     * 将UserDTO转换为UserResponse
     *
     * @param dto 用户DTO
     * @return 用户响应DTO
     */
    @Mapping(target = "createdTime", source = "createdTime")
    @Mapping(target = "updatedTime", source = "updatedTime")
    UserResponse toResponse(UserDTO dto);

    /**
     * 将UserDTO列表转换为UserResponse列表
     *
     * @param dtos 用户DTO列表
     * @return 用户响应DTO列表
     */
    List<UserResponse> toResponseList(List<UserDTO> dtos);
    
    /**
     * 将UserEntity直接转换为UserResponse
     * <p>
     * 用于更新用户后直接返回响应，避免中间转换步骤
     * </p>
     *
     * @param entity 用户实体
     * @return 用户响应DTO
     */
    @Mapping(target = "createdTime", source = "createdAt")
    @Mapping(target = "updatedTime", source = "updatedAt")
    UserResponse entityToResponse(UserEntity entity);

    /**
     * 将UserEntity列表直接转换为UserResponse列表
     *
     * @param entities 用户实体列表
     * @return 用户响应DTO列表
     */
    List<UserResponse> entitiesToResponseList(List<UserEntity> entities);

    /**
     * 创建Email值对象
     *
     * @param email 邮箱地址
     * @return Email值对象
     */
    default Email mapToEmail(String email) {
        return email != null ? Email.of(email) : null;
    }

    /**
     * 从Email值对象获取邮箱地址
     *
     * @param email Email值对象
     * @return 邮箱地址
     */
    default String mapFromEmail(Email email) {
        return email != null ? email.getAddress() : null;
    }

    /**
     * 创建PhoneNumber值对象
     *
     * @param phone       电话号码
     * @param countryCode 国家代码
     * @return PhoneNumber值对象
     */
    default PhoneNumber mapToPhoneNumber(String phone, String countryCode) {
        return (phone != null && countryCode != null) ? PhoneNumber.of(phone, countryCode) : null;
    }

    /**
     * 创建Address值对象
     *
     * @param country       国家
     * @param province      省份
     * @param city          城市
     * @param district      区县
     * @param addressDetail 详细地址
     * @param zipCode       邮编
     * @return Address值对象
     */
    default Address mapToAddress(String country, String province, String city, String district, String addressDetail, String zipCode) {
        if (country != null && province != null && city != null && district != null && addressDetail != null && zipCode != null) {
            return Address.of(country, province, city, district, addressDetail, zipCode);
        }
        return null;
    }
}