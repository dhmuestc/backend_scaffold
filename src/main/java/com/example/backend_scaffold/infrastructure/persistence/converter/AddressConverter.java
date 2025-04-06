package com.example.backend_scaffold.infrastructure.persistence.converter;

import com.example.backend_scaffold.domain.model.valueobject.Address;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

/**
 * 地址转换器
 * <p>
 * 用于将地址值对象转换为数据库存储格式
 * </p>
 *
 * @author example
 */
@Converter(autoApply = true)
public class AddressConverter implements AttributeConverter<Address, String> {

    /**
     * 将地址值对象转换为数据库列值
     *
     * @param attribute 地址值对象
     * @return 数据库列值
     */
    @Override
    public String convertToDatabaseColumn(Address attribute) {
        return attribute != null ? attribute.getFullAddress() : null;
    }

    /**
     * 将数据库列值转换为地址值对象
     *
     * @param dbData 数据库列值
     * @return 地址值对象
     */
    @Override
    public Address convertToEntityAttribute(String dbData) {
        return dbData != null ? Address.of(dbData) : null;
    }
}