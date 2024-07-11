package com.sevenhallo.productservice.service;

import com.sevenhallo.productservice.dto.ProductDto;
import com.sevenhallo.productservice.mapper.ProductMapper;
import com.sevenhallo.productservice.model.Product;
import com.sevenhallo.productservice.repository.ProductRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@Transactional
public class ProductService {
    private final ProductRepository repository;
    private final ProductMapper productMapper;

    public ProductService(ProductRepository repository, ProductMapper productMapper) {
        this.repository = repository;
        this.productMapper = productMapper;
    }

    public ProductDto save(ProductDto productDto) {
        Product entity = productMapper.toEntity(productDto);
        return productMapper.toDto(repository.save(entity));
    }

    public void deleteById(String id) {
        repository.deleteById(id);
    }

    public ProductDto findById(String id) {
        return productMapper.toDto(repository.findById(id)
                .orElseThrow(ResourceNotFoundException::new));
    }

    public Page<ProductDto> findByCondition(ProductDto productDto, Pageable pageable) {
        Page<Product> entityPage = repository.findAll(pageable);
        List<Product> entities = entityPage.getContent();
        return new PageImpl<>(productMapper.toDto(entities), pageable, entityPage.getTotalElements());
    }

    public ProductDto update(ProductDto productDto, String id) {
        ProductDto data = findById(id);
        Product entity = productMapper.toEntity(productDto);
        BeanUtils.copyProperties(data, entity);
        return save(productMapper.toDto(entity));
    }
}
