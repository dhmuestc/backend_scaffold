package com.example.backend_scaffold.domain.model.valueobject;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 地址值对象
 * <p>
 * 用于封装地址信息的处理逻辑
 * </p>
 *
 * @author example
 */
@Getter
@EqualsAndHashCode
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Address implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 国家
     */
    private String country;

    /**
     * 省/州
     */
    private String province;

    /**
     * 市
     */
    private String city;

    /**
     * 区/县
     */
    private String district;

    /**
     * 详细地址
     */
    private String detail;

    /**
     * 邮政编码
     */
    private String zipCode;

    /**
     * 创建地址值对象
     *
     * @param country  国家
     * @param province 省/州
     * @param city     市
     * @param district 区/县
     * @param detail   详细地址
     * @param zipCode  邮政编码
     * @return 地址值对象
     */
    public static Address of(String country, String province, String city, String district, String detail, String zipCode) {
        Address instance = new Address();
        instance.setCountry(country);
        instance.setProvince(province);
        instance.setCity(city);
        instance.setDistrict(district);
        instance.setDetail(detail);
        instance.setZipCode(zipCode);
        return instance;
    }

    /**
     * 设置国家
     *
     * @param country 国家
     */
    private void setCountry(String country) {
        if (country == null || country.trim().isEmpty()) {
            throw new IllegalArgumentException("Country cannot be null or empty");
        }
        this.country = country.trim();
    }

    /**
     * 设置省/州
     *
     * @param province 省/州
     */
    private void setProvince(String province) {
        if (province == null || province.trim().isEmpty()) {
            throw new IllegalArgumentException("Province cannot be null or empty");
        }
        this.province = province.trim();
    }

    /**
     * 设置市
     *
     * @param city 市
     */
    private void setCity(String city) {
        if (city == null || city.trim().isEmpty()) {
            throw new IllegalArgumentException("City cannot be null or empty");
        }
        this.city = city.trim();
    }

    /**
     * 设置区/县
     *
     * @param district 区/县
     */
    private void setDistrict(String district) {
        if (district == null || district.trim().isEmpty()) {
            throw new IllegalArgumentException("District cannot be null or empty");
        }
        this.district = district.trim();
    }

    /**
     * 设置详细地址
     *
     * @param detail 详细地址
     */
    private void setDetail(String detail) {
        if (detail == null || detail.trim().isEmpty()) {
            throw new IllegalArgumentException("Detail cannot be null or empty");
        }
        this.detail = detail.trim();
    }

    /**
     * 设置邮政编码
     *
     * @param zipCode 邮政编码
     */
    private void setZipCode(String zipCode) {
        if (zipCode == null || zipCode.trim().isEmpty()) {
            throw new IllegalArgumentException("Zip code cannot be null or empty");
        }
        this.zipCode = zipCode.trim();
    }

    /**
     * 获取完整地址字符串
     *
     * @return 完整地址字符串
     */
    public String getFullAddress() {
        return String.format("%s %s %s %s %s %s", country, province, city, district, detail, zipCode);
    }
    
    /**
     * 从字符串创建地址值对象
     * <p>
     * 字符串格式应为："country province city district detail zipCode"
     * </p>
     *
     * @param addressString 地址字符串
     * @return 地址值对象
     * @throws IllegalArgumentException 如果地址字符串格式不正确
     */
    public static Address of(String addressString) {
        if (addressString == null || addressString.trim().isEmpty()) {
            throw new IllegalArgumentException("Address string cannot be null or empty");
        }
        
        String[] parts = addressString.trim().split("\\s+");
        if (parts.length < 6) {
            throw new IllegalArgumentException("Invalid address format. Expected format: 'country province city district detail zipCode'");
        }
        
        String country = parts[0];
        String province = parts[1];
        String city = parts[2];
        String district = parts[3];
        
        // The detail part might contain spaces, so we need to handle it specially
        // We'll assume the last part is the zipCode and everything else between district and zipCode is the detail
        String zipCode = parts[parts.length - 1];
        
        StringBuilder detailBuilder = new StringBuilder();
        for (int i = 4; i < parts.length - 1; i++) {
            detailBuilder.append(parts[i]);
            if (i < parts.length - 2) {
                detailBuilder.append(" ");
            }
        }
        String detail = detailBuilder.toString();
        
        return of(country, province, city, district, detail, zipCode);
    }
}