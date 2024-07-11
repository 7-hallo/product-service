package com.sevenhallo.productservice.mapper;

import com.sevenhallo.productservice.dto.ProductDto;
import com.sevenhallo.productservice.model.Product;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProductMapper extends EntityMapper<ProductDto, Product> {

}