package com.example.backend_scaffold.domain.service.impl;

import com.example.backend_scaffold.domain.model.entity.UserEntity;
import com.example.backend_scaffold.domain.model.entity.UserRoleEntity;
import com.example.backend_scaffold.domain.model.enums.UserStatus;
import com.example.backend_scaffold.domain.repository.UserRepository;
import com.example.backend_scaffold.domain.repository.UserRoleRepository;
import com.example.backend_scaffold.domain.service.UserDomainService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * 用户领域服务实现类
 * <p>
 * 实现用户相关的业务逻辑
 * </p>
 *
 * @author example
 */
@Service
@RequiredArgsConstructor
public class UserDomainServiceImpl implements UserDomainService {

    private final UserRepository userRepository;
    private final UserRoleRepository userRoleRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public UserEntity createUser(UserEntity userEntity) {
        // 检查用户名是否已存在
        if (userRepository.findByUsername(userEntity.getUsername()).isPresent()) {
            throw new RuntimeException("Username already exists: " + userEntity.getUsername());
        }

        // 检查邮箱是否已存在
        if (userEntity.getEmail() != null && userRepository.findByEmail(userEntity.getEmail()).isPresent()) {
            throw new RuntimeException("Email already exists: " + userEntity.getEmail());
        }

        // 检查手机号是否已存在
        if (userEntity.getPhone() != null && userRepository.findByPhone(userEntity.getPhone()).isPresent()) {
            throw new RuntimeException("Phone already exists: " + userEntity.getPhone());
        }

        // 加密密码
        userEntity.setPassword(encodePassword(userEntity.getPassword()));

        // 设置默认状态
        if (userEntity.getStatus() == null) {
            userEntity.setStatus(UserStatus.ACTIVE);
        }

        return userRepository.save(userEntity);
    }

    @Override
    @Transactional
    public UserEntity updateUser(UserEntity userEntity) {
        // 查询原用户信息
        UserEntity existingUser = userRepository.findById(userEntity.getId())
                .orElseThrow(() -> new RuntimeException("User not found with id: " + userEntity.getId()));

        // 检查用户名是否已存在（排除自身）
        if (!existingUser.getUsername().equals(userEntity.getUsername())
                && userRepository.findByUsername(userEntity.getUsername()).isPresent()) {
            throw new RuntimeException("Username already exists: " + userEntity.getUsername());
        }

        // 检查邮箱是否已存在（排除自身）
        if (userEntity.getEmail() != null && !userEntity.getEmail().equals(existingUser.getEmail())
                && userRepository.findByEmail(userEntity.getEmail()).isPresent()) {
            throw new RuntimeException("Email already exists: " + userEntity.getEmail());
        }

        // 检查手机号是否已存在（排除自身）
        if (userEntity.getPhone() != null && !userEntity.getPhone().equals(existingUser.getPhone())
                && userRepository.findByPhone(userEntity.getPhone()).isPresent()) {
            throw new RuntimeException("Phone already exists: " + userEntity.getPhone());
        }

        // 如果密码字段不为空，则加密密码
        if (userEntity.getPassword() != null && !userEntity.getPassword().isEmpty()) {
            userEntity.setPassword(encodePassword(userEntity.getPassword()));
        } else {
            // 保留原密码
            userEntity.setPassword(existingUser.getPassword());
        }

        return userRepository.save(userEntity);
    }

    @Override
    @Transactional
    public void deleteUser(Long id) {
        // 检查用户是否存在
        if (!userRepository.findById(id).isPresent()) {
            throw new RuntimeException("User not found with id: " + id);
        }

        // 删除用户角色关联
        userRoleRepository.deleteByUserId(id);

        // 删除用户（或者将用户状态设置为已删除）
        UserEntity userEntity = userRepository.findById(id).get();
        userEntity.setStatus(UserStatus.DELETED);
        userRepository.save(userEntity);
    }

    @Override
    public Optional<UserEntity> findById(Long id) {
        return userRepository.findById(id);
    }

    @Override
    public Optional<UserEntity> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public Optional<UserEntity> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public Optional<UserEntity> findByPhone(String phone) {
        return userRepository.findByPhone(phone);
    }

    @Override
    public List<UserEntity> findAll() {
        return userRepository.findAll();
    }
    
    @Override
    public Page<UserEntity> findAll(Pageable pageable) {
        return userRepository.findAll(pageable);
    }

    @Override
    public List<UserEntity> findByStatus(UserStatus status) {
        return userRepository.findByStatus(status);
    }
    
    @Override
    public Page<UserEntity> findByStatus(UserStatus status, Pageable pageable) {
        return userRepository.findByStatus(status, pageable);
    }

    @Override
    @Transactional
    public UserRoleEntity assignRole(Long userId, Long roleId) {
        // 检查用户是否存在
        if (!userRepository.findById(userId).isPresent()) {
            throw new RuntimeException("User not found with id: " + userId);
        }

        // 检查用户角色关联是否已存在
        Optional<UserRoleEntity> existingUserRole = userRoleRepository.findByUserIdAndRoleId(userId, roleId);
        if (existingUserRole.isPresent()) {
            // 如果已存在但被禁用，则重新启用
            if (!existingUserRole.get().getIsEnabled()) {
                UserRoleEntity userRole = existingUserRole.get();
                userRole.setIsEnabled(true);
                return userRoleRepository.save(userRole);
            }
            return existingUserRole.get();
        }

        // 创建新的用户角色关联
        UserRoleEntity userRole = new UserRoleEntity();
        userRole.setUserId(userId);
        userRole.setRoleId(roleId);
        userRole.setIsEnabled(true);
        return userRoleRepository.save(userRole);
    }

    @Override
    @Transactional
    public void removeRole(Long userId, Long roleId) {
        userRoleRepository.deleteByUserIdAndRoleId(userId, roleId);
    }

    @Override
    public List<Long> getUserRoleIds(Long userId) {
        return userRoleRepository.findByUserId(userId).stream()
                .filter(UserRoleEntity::getIsEnabled)
                .map(UserRoleEntity::getRoleId)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public boolean changePassword(Long userId, String oldPassword, String newPassword) {
        UserEntity userEntity = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + userId));

        // 验证旧密码
        if (!verifyPassword(oldPassword, userEntity.getPassword())) {
            return false;
        }

        // 更新密码
        userEntity.setPassword(encodePassword(newPassword));
        userRepository.save(userEntity);
        return true;
    }

    @Override
    @Transactional
    public boolean resetPassword(Long userId, String newPassword) {
        UserEntity userEntity = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + userId));

        // 更新密码
        userEntity.setPassword(encodePassword(newPassword));
        userRepository.save(userEntity);
        return true;
    }

    @Override
    @Transactional
    public boolean lockUser(Long userId) {
        UserEntity userEntity = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + userId));

        if (userEntity.getStatus() == UserStatus.LOCKED) {
            return true; // 已经是锁定状态
        }

        userEntity.setStatus(UserStatus.LOCKED);
        userRepository.save(userEntity);
        return true;
    }

    @Override
    @Transactional
    public boolean unlockUser(Long userId) {
        UserEntity userEntity = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + userId));

        if (userEntity.getStatus() != UserStatus.LOCKED) {
            return false; // 不是锁定状态，无法解锁
        }

        userEntity.setStatus(UserStatus.ACTIVE);
        userRepository.save(userEntity);
        return true;
    }

    @Override
    @Transactional
    public boolean enableUser(Long userId) {
        UserEntity userEntity = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + userId));

        if (userEntity.getStatus() == UserStatus.ACTIVE) {
            return true; // 已经是启用状态
        }

        if (userEntity.getStatus() == UserStatus.DELETED) {
            return false; // 已删除用户不能启用
        }

        userEntity.setStatus(UserStatus.ACTIVE);
        userRepository.save(userEntity);
        return true;
    }

    @Override
    @Transactional
    public boolean disableUser(Long userId) {
        UserEntity userEntity = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + userId));

        if (userEntity.getStatus() == UserStatus.DISABLED) {
            return true; // 已经是禁用状态
        }

        if (userEntity.getStatus() == UserStatus.DELETED) {
            return false; // 已删除用户不能禁用
        }

        userEntity.setStatus(UserStatus.DISABLED);
        userRepository.save(userEntity);
        return true;
    }

    @Override
    public boolean verifyPassword(String rawPassword, String encodedPassword) {
        return passwordEncoder.matches(rawPassword, encodedPassword);
    }

    @Override
    public String encodePassword(String rawPassword) {
        return passwordEncoder.encode(rawPassword);
    }
}