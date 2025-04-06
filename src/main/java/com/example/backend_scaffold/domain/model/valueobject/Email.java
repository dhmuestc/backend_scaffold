package com.example.backend_scaffold.domain.model.valueobject;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.regex.Pattern;

/**
 * 邮箱值对象
 * <p>
 * 用于封装邮箱地址的处理逻辑
 * </p>
 *
 * @author example
 */
@Getter
@EqualsAndHashCode
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Email implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 邮箱地址正则表达式
     */
    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$");

    /**
     * 邮箱地址
     */
    private String address;

    /**
     * 创建邮箱值对象
     *
     * @param address 邮箱地址
     * @return 邮箱值对象
     */
    public static Email of(String address) {
        Email instance = new Email();
        instance.setAddress(address);
        return instance;
    }

    /**
     * 设置邮箱地址
     *
     * @param address 邮箱地址
     */
    private void setAddress(String address) {
        if (address == null || address.trim().isEmpty()) {
            throw new IllegalArgumentException("Email address cannot be null or empty");
        }
        if (!EMAIL_PATTERN.matcher(address.trim()).matches()) {
            throw new IllegalArgumentException("Invalid email address format");
        }
        this.address = address.trim();
    }

    /**
     * 获取邮箱的域名部分
     *
     * @return 邮箱域名
     */
    public String getDomain() {
        return address.substring(address.indexOf('@') + 1);
    }

    /**
     * 获取邮箱的用户名部分
     *
     * @return 邮箱用户名
     */
    public String getUsername() {
        return address.substring(0, address.indexOf('@'));
    }

    /**
     * 获取邮箱地址
     *
     * @return 邮箱地址
     */
    public String getValue() {
        return address;
    }

    @Override
    public String toString() {
        return address;
    }
}