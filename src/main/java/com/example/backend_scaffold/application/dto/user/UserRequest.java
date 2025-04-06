package com.example.backend_scaffold.application.dto.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 用户请求DTO
 * <p>
 * 用于创建或更新用户的请求数据传输对象
 * </p>
 *
 * @author example
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserRequest {

    /**
     * 用户ID（更新时使用）
     */
    private Long id;

    /**
     * 用户名
     */
    @NotBlank(message = "用户名不能为空")
    @Size(min = 4, max = 50, message = "用户名长度必须在4-50个字符之间")
    @Pattern(regexp = "^[a-zA-Z0-9_]+$", message = "用户名只能包含字母、数字和下划线")
    private String username;

    /**
     * 密码（创建时必填）
     */
    @Size(min = 6, max = 100, message = "密码长度必须在6-100个字符之间")
    private String password;

    /**
     * 昵称
     */
    @Size(max = 50, message = "昵称长度不能超过50个字符")
    private String nickname;

    /**
     * 真实姓名
     */
    @Size(max = 50, message = "真实姓名长度不能超过50个字符")
    private String realName;

    /**
     * 头像URL
     */
    @Size(max = 255, message = "头像URL长度不能超过255个字符")
    private String avatar;

    /**
     * 邮箱
     */
    @Email(message = "邮箱格式不正确")
    @Size(max = 100, message = "邮箱长度不能超过100个字符")
    private String email;

    /**
     * 手机号
     */
    @Pattern(regexp = "^[0-9]+$", message = "手机号只能包含数字")
    @Size(max = 20, message = "手机号长度不能超过20个字符")
    private String phone;

    /**
     * 国家代码
     */
    @Size(max = 10, message = "国家代码长度不能超过10个字符")
    private String countryCode;

    /**
     * 地址信息
     */
    @Size(max = 50, message = "国家名称长度不能超过50个字符")
    private String country;
    
    @Size(max = 50, message = "省份名称长度不能超过50个字符")
    private String province;
    
    @Size(max = 50, message = "城市名称长度不能超过50个字符")
    private String city;
    
    @Size(max = 50, message = "区县名称长度不能超过50个字符")
    private String district;
    
    @Size(max = 200, message = "详细地址长度不能超过200个字符")
    private String addressDetail;
    
    @Size(max = 20, message = "邮政编码长度不能超过20个字符")
    private String zipCode;
}