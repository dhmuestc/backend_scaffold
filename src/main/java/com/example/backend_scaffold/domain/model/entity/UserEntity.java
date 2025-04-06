package com.example.backend_scaffold.domain.model.entity;

import com.example.backend_scaffold.domain.model.enums.UserStatus;
import com.example.backend_scaffold.domain.model.valueobject.Address;
import com.example.backend_scaffold.domain.model.valueobject.Email;
import com.example.backend_scaffold.domain.model.valueobject.PhoneNumber;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * 用户实体类
 * <p>
 * 用于存储用户基本信息
 * </p>
 *
 * @author example
 */
@Getter
@Setter
public class UserEntity extends BaseEntity {

    private Long id;

    /**
     * 用户名
     */
    private String username;

    /**
     * 密码（加密存储）
     */
    private String password;

    /**
     * 昵称
     */
    private String nickname;

    /**
     * 真实姓名
     */
    private String realName;

    /**
     * 头像URL
     */
    private String avatar;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 手机号
     */
    private String phone;

    /**
     * 国家代码
     */
    private String countryCode;

    /**
     * 用户状态
     */
    private UserStatus status;

    /**
     * 最后登录时间
     */
    private LocalDateTime lastLoginTime;

    /**
     * 最后登录IP
     */
    private String lastLoginIp;

    /**
     * 地址信息（国家、省、市、区、详细地址、邮编）
     */
    private String country;
    private String province;
    private String city;
    private String district;
    private String addressDetail;
    private String zipCode;

    /**
     * 获取邮箱值对象
     *
     * @return 邮箱值对象
     */
    public Email getEmailObject() {
        return email != null ? Email.of(email) : null;
    }

    /**
     * 设置邮箱值对象
     *
     * @param emailObject 邮箱值对象
     */
    public void setEmailObject(Email emailObject) {
        this.email = emailObject != null ? emailObject.getAddress() : null;
    }

    /**
     * 获取电话号码值对象
     *
     * @return 电话号码值对象
     */
    public PhoneNumber getPhoneObject() {
        return (phone != null && countryCode != null) ? PhoneNumber.of(phone, countryCode) : null;
    }

    /**
     * 设置电话号码值对象
     *
     * @param phoneObject 电话号码值对象
     */
    public void setPhoneObject(PhoneNumber phoneObject) {
        if (phoneObject != null) {
            this.phone = phoneObject.getNumber();
            this.countryCode = phoneObject.getCountryCode();
        } else {
            this.phone = null;
            this.countryCode = null;
        }
    }

    /**
     * 获取地址值对象
     *
     * @return 地址值对象
     */
    public Address getAddressObject() {
        if (country != null && province != null && city != null && district != null && addressDetail != null && zipCode != null) {
            return Address.of(country, province, city, district, addressDetail, zipCode);
        }
        return null;
    }

    /**
     * 设置地址值对象
     *
     * @param addressObject 地址值对象
     */
    public void setAddressObject(Address addressObject) {
        if (addressObject != null) {
            this.country = addressObject.getCountry();
            this.province = addressObject.getProvince();
            this.city = addressObject.getCity();
            this.district = addressObject.getDistrict();
            this.addressDetail = addressObject.getDetail();
            this.zipCode = addressObject.getZipCode();
        } else {
            this.country = null;
            this.province = null;
            this.city = null;
            this.district = null;
            this.addressDetail = null;
            this.zipCode = null;
        }
    }
    
    /**
     * 判断用户是否启用
     * <p>
     * 用于Spring Security的User构造函数
     * </p>
     *
     * @return 如果用户状态为ACTIVE则返回true，否则返回false
     */
    public boolean isEnabled() {
        return status == UserStatus.ACTIVE;
    }
}