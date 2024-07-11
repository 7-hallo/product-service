package com.sevenhallo.productservice.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Schema
@Getter
@Setter
@NoArgsConstructor
public class ProductDto extends AbstractDto<String> {
    private String id;
    private String name;
    private String description;
    private BigDecimal price;
}