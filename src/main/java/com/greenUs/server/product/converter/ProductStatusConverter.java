package com.greenUs.server.product.converter;

import com.greenUs.server.product.domain.ProductStatus;

import javax.persistence.AttributeConverter;

public class ProductStatusConverter implements AttributeConverter<ProductStatus, String> {

    @Override
    public String convertToDatabaseColumn(ProductStatus attribute) {
        if (attribute == null)
            return null;
        return attribute.getCode();
    }

    @Override
    public ProductStatus convertToEntityAttribute(String dbData) {
        if (dbData == null)
            return null;
        return ProductStatus.ofCode(dbData);
    }
}
