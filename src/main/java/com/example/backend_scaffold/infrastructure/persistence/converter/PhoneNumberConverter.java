package com.example.backend_scaffold.infrastructure.persistence.converter;

import com.example.backend_scaffold.domain.model.valueobject.PhoneNumber;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

/**
 * 电话号码转换器
 * <p>
 * 用于将电话号码值对象转换为数据库存储格式
 * </p>
 *
 * @author example
 */
@Converter(autoApply = true)
public class PhoneNumberConverter implements AttributeConverter<PhoneNumber, String> {

    /**
     * 将电话号码值对象转换为数据库列值
     *
     * @param attribute 电话号码值对象
     * @return 数据库列值
     */
    @Override
    public String convertToDatabaseColumn(PhoneNumber attribute) {
        return attribute != null ? attribute.getNumber() : null;
    }

    /**
     * 将数据库列值转换为电话号码值对象
     *
     * @param dbData 数据库列值
     * @return 电话号码值对象
     */
    @Override
    public PhoneNumber convertToEntityAttribute(String dbData) {
        return dbData != null ? PhoneNumber.of(dbData) : null;
    }
}