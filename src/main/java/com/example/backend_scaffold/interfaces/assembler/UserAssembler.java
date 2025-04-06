package com.example.backend_scaffold.interfaces.assembler;

import com.example.backend_scaffold.application.dto.user.UserDTO;
import com.example.backend_scaffold.application.dto.user.UserResponse;
import com.example.backend_scaffold.domain.model.aggregate.User;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 用户组装器，负责在领域模型和DTO之间进行转换
 */
@Component
public class UserAssembler {

    /**
     * 将用户聚合根转换为用户响应DTO
     *
     * @param user 用户聚合根
     * @return 用户响应DTO
     */
    public UserResponse toResponse(User user) {
        if (user == null) {
            return null;
        }
        
        return UserResponse.builder()
                .id(user.getId())
                .username(user.getUsername())
                .email(user.getEmail() != null ? user.getEmail().getValue() : null)
                .phone(user.getPhoneNumber() != null ? user.getPhoneNumber().getValue() : null)
                .realName(user.getFullName())
                .status(user.getStatus())
                .createdTime(user.getCreatedAt())
                .updatedTime(user.getUpdatedAt())
                .build();
    }

    /**
     * 将用户聚合根列表转换为用户响应DTO列表
     *
     * @param users 用户聚合根列表
     * @return 用户响应DTO列表
     */
    public List<UserResponse> toResponseList(List<User> users) {
        if (users == null) {
            return null;
        }
        
        return users.stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    /**
     * 将用户聚合根转换为用户DTO
     *
     * @param user 用户聚合根
     * @return 用户DTO
     */
    public UserDTO toDTO(User user) {
        if (user == null) {
            return null;
        }
        
        return UserDTO.builder()
                .id(user.getId())
                .username(user.getUsername())
                .email(user.getEmail() != null ? user.getEmail().getValue() : null)
                .phone(user.getPhoneNumber() != null ? user.getPhoneNumber().getValue() : null)
                .realName(user.getFullName())
                .status(user.getStatus())
                .build();
    }

    /**
     * 将用户聚合根列表转换为用户DTO列表
     *
     * @param users 用户聚合根列表
     * @return 用户DTO列表
     */
    public List<UserDTO> toDTOList(List<User> users) {
        if (users == null) {
            return null;
        }
        
        return users.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }
}