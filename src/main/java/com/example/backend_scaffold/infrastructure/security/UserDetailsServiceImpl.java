package com.example.backend_scaffold.infrastructure.security;

import com.example.backend_scaffold.domain.model.entity.UserEntity;
import com.example.backend_scaffold.domain.repository.UserRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 用户详情服务实现类
 * <p>
 * 实现Spring Security的UserDetailsService接口，用于加载用户信息
 * </p>
 *
 * @author example
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    /**
     * 用户仓储
     */
    private final UserRepository userRepository;

    /**
     * 构造函数
     *
     * @param userRepository 用户仓储
     */
    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * 根据用户名加载用户
     *
     * @param username 用户名
     * @return 用户详情
     * @throws UsernameNotFoundException 用户名未找到异常
     */
    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username)
                .map(this::createUserDetails)
                .orElseThrow(() -> new UsernameNotFoundException("用户不存在: " + username));
    }

    /**
     * 创建用户详情
     *
     * @param userEntity 用户实体
     * @return 用户详情
     */
    private UserDetails createUserDetails(UserEntity userEntity) {
        // 获取用户角色和权限
        List<SimpleGrantedAuthority> authorities = userRepository.findUserRolesAndPermissions(userEntity.getId())
                .stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());

        // 创建Spring Security的User对象
        return new User(
                userEntity.getUsername(),
                userEntity.getPassword(),
                userEntity.isEnabled(),
                true, // accountNonExpired
                true, // credentialsNonExpired
                userEntity.getStatus() != com.example.backend_scaffold.domain.model.enums.UserStatus.LOCKED, // accountNonLocked
                authorities
        );
    }
}