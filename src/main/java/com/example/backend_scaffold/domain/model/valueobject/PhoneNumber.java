package com.example.backend_scaffold.domain.model.valueobject;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.regex.Pattern;

/**
 * 电话号码值对象
 * <p>
 * 用于封装电话号码的处理逻辑
 * </p>
 *
 * @author example
 */
@Getter
@EqualsAndHashCode
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PhoneNumber implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 电话号码正则表达式
     * 支持国内手机号码格式
     */
    private static final Pattern PHONE_PATTERN = Pattern.compile("^1[3-9]\\d{9}$");

    /**
     * 电话号码
     */
    private String number;

    /**
     * 国家代码
     */
    private String countryCode;

    /**
     * 创建电话号码值对象
     *
     * @param number      电话号码
     * @param countryCode 国家代码
     * @return 电话号码值对象
     */
    public static PhoneNumber of(String number, String countryCode) {
        PhoneNumber instance = new PhoneNumber();
        instance.setNumber(number);
        instance.setCountryCode(countryCode);
        return instance;
    }
    
    /**
     * 创建电话号码值对象（使用默认国家代码）
     *
     * @param number 电话号码
     * @return 电话号码值对象
     */
    public static PhoneNumber of(String number) {
        return of(number, "86"); // 使用中国国家代码作为默认值
    }

    /**
     * 设置电话号码
     *
     * @param number 电话号码
     */
    private void setNumber(String number) {
        if (number == null || number.trim().isEmpty()) {
            throw new IllegalArgumentException("Phone number cannot be null or empty");
        }
        if (!PHONE_PATTERN.matcher(number.trim()).matches()) {
            throw new IllegalArgumentException("Invalid phone number format");
        }
        this.number = number.trim();
    }

    /**
     * 设置国家代码
     *
     * @param countryCode 国家代码
     */
    private void setCountryCode(String countryCode) {
        if (countryCode == null || countryCode.trim().isEmpty()) {
            throw new IllegalArgumentException("Country code cannot be null or empty");
        }
        this.countryCode = countryCode.trim();
    }

    /**
     * 获取完整电话号码（包含国家代码）
     *
     * @return 完整电话号码
     */
    public String getFullNumber() {
        return String.format("+%s%s", countryCode, number);
    }
    
    /**
     * 获取电话号码
     *
     * @return 电话号码
     */
    public String getValue() {
        return number;
    }

    @Override
    public String toString() {
        return getFullNumber();
    }
}