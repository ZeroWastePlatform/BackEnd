package com.greenUs.server.product.converter;

import com.greenUs.server.product.domain.Badge;

import javax.persistence.AttributeConverter;

public class BadgeConverter implements AttributeConverter<Badge, String> {

    @Override
    public String convertToDatabaseColumn(Badge attribute) {
        if (attribute == null)
            return null;
        return attribute.getCode();
    }

    @Override
    public Badge convertToEntityAttribute(String dbData) {
        if (dbData == null)
            return null;
        return Badge.ofCode(dbData);
    }
}
