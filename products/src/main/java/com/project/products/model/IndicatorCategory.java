package com.project.products.model;

import lombok.Getter;

import java.util.Arrays;

@Getter
@Deprecated
public enum IndicatorCategory {
    FRUITS(1),
    VEGETABLES(2),
    CANNED_GOODS(3),
    DAIRY(4),
    MEAT(5);

    private final Integer code;

    IndicatorCategory(Integer code) {
        this.code = code;
    }

    public static IndicatorCategory getByCode(Integer code) {
        return Arrays.stream(IndicatorCategory.values())
                .filter(v -> v.code.equals(code))
                .findAny()
                .orElseThrow(() -> new RuntimeException(String.format("IndicatorCategory does not exist for code '%s'.", code)));
    }

}

