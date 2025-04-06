package com.example.backend_scaffold.infrastructure.persistence.converter;

import com.example.backend_scaffold.domain.model.valueobject.Email;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

/**
 * 邮箱转换器
 * <p>
 * 用于将邮箱值对象转换为数据库存储格式
 * </p>
 *
 * @author example
 */
@Converter(autoApply = true)
public class EmailConverter implements AttributeConverter<Email, String> {

    /**
     * 将邮箱值对象转换为数据库列值
     *
     * @param attribute 邮箱值对象
     * @return 数据库列值
     */
    @Override
    public String convertToDatabaseColumn(Email attribute) {
        return attribute != null ? attribute.getAddress() : null;
    }

    /**
     * 将数据库列值转换为邮箱值对象
     *
     * @param dbData 数据库列值
     * @return 邮箱值对象
     */
    @Override
    public Email convertToEntityAttribute(String dbData) {
        return dbData != null ? Email.of(dbData) : null;
    }
}