package com.greenUs.server.product.converter;

import com.greenUs.server.product.domain.Category;

import javax.persistence.AttributeConverter;

public class CategoryConverter implements AttributeConverter<Category, String> {

    @Override
    public String convertToDatabaseColumn(Category attribute) {
        if (attribute == null)
            return null;
        return attribute.getCode();
    }

    @Override
    public Category convertToEntityAttribute(String dbData) {
        if (dbData == null)
            return null;
        return Category.ofCode(dbData);
    }
}
