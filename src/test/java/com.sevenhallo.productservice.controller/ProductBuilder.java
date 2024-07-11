package com.sevenhallo.productservice.controller;

import com.sevenhallo.productservice.dto.ProductDto;

import java.util.Collections;
import java.util.List;

public class ProductBuilder {
    public static List<String> getIds() {
        return Collections.singletonList("1");
    }

    public static ProductDto getDto() {
        ProductDto dto = new ProductDto();
        dto.setId("1");
        return dto;
    }
}
