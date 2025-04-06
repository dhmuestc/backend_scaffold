package com.example.backend_scaffold.infrastructure.persistence.entity;

import com.example.backend_scaffold.domain.model.enums.UserStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * 用户JPA实体类
 * <p>
 * 用于存储用户基本信息的JPA实体，负责与数据库交互
 * </p>
 *
 * @author example
 */
@Entity
@Table(name = "users")
@Getter
@Setter
public class UserJpaEntity extends BaseJpaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 用户名
     */
    @Column(name = "username", nullable = false, unique = true, length = 50)
    private String username;

    /**
     * 密码（加密存储）
     */
    @Column(name = "password", nullable = false, length = 100)
    private String password;

    /**
     * 昵称
     */
    @Column(name = "nickname", length = 50)
    private String nickname;

    /**
     * 真实姓名
     */
    @Column(name = "real_name", length = 50)
    private String realName;

    /**
     * 头像URL
     */
    @Column(name = "avatar", length = 255)
    private String avatar;

    /**
     * 邮箱
     */
    @Column(name = "email", length = 100)
    private String email;

    /**
     * 手机号
     */
    @Column(name = "phone", length = 20)
    private String phone;

    /**
     * 国家代码
     */
    @Column(name = "country_code", length = 10)
    private String countryCode;

    /**
     * 用户状态
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private UserStatus status;

    /**
     * 最后登录时间
     */
    @Column(name = "last_login_time")
    private LocalDateTime lastLoginTime;

    /**
     * 最后登录IP
     */
    @Column(name = "last_login_ip", length = 50)
    private String lastLoginIp;

    /**
     * 地址信息（国家、省、市、区、详细地址、邮编）
     */
    @Column(name = "country", length = 50)
    private String country;

    @Column(name = "province", length = 50)
    private String province;

    @Column(name = "city", length = 50)
    private String city;

    @Column(name = "district", length = 50)
    private String district;

    @Column(name = "address_detail", length = 200)
    private String addressDetail;

    @Column(name = "zip_code", length = 20)
    private String zipCode;



    /**
     * 判断用户是否启用
     * <p>
     * 用于Spring Security的User构造函数
     * </p>
     *
     * @return 如果用户状态为ACTIVE则返回true，否则返回false
     */
    @Transient
    public boolean isEnabled() {
        return status == UserStatus.ACTIVE;
    }
}