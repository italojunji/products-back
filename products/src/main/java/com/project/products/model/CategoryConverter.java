package com.project.products.model;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.util.stream.Stream;

@Converter(autoApply = true)
public class CategoryConverter implements AttributeConverter<IndicatorCategory, Integer> {

    @Override
    public Integer convertToDatabaseColumn(IndicatorCategory category) {
        if (category == null) {
            return null;
        }
        return category.getCode();
    }

    @Override
    public IndicatorCategory convertToEntityAttribute(Integer code) {
        if (code == null) {
            return null;
        }

        return Stream.of(IndicatorCategory.values())
                .filter(c -> c.getCode().equals(code))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }
}
