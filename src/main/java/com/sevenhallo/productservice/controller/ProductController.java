package com.sevenhallo.productservice.controller;

import com.sevenhallo.productservice.dto.ProductDto;
import com.sevenhallo.productservice.mapper.ProductMapper;
import com.sevenhallo.productservice.model.Product;
import com.sevenhallo.productservice.service.ProductService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequestMapping("/api/product")
@RestController
@Slf4j
@Tag(name = "product")
public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping
    public ResponseEntity<Void> save(@RequestBody @Validated ProductDto productDto) {
        productService.save(productDto);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductDto> findById(@PathVariable("id") String id) {
        ProductDto product = productService.findById(id);
        return ResponseEntity.ok(product);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") String id) {
        Optional.ofNullable(productService.findById(id)).orElseThrow(() -> {
            log.error("Unable to delete non-existent data!");
            return new ResourceNotFoundException();
        });
        productService.deleteById(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/page-query")
    public ResponseEntity<Page<ProductDto>> pageQuery(ProductDto productDto, @PageableDefault(sort = "createAt", direction = Sort.Direction.DESC) Pageable pageable) {
        Page<ProductDto> productPage = productService.findByCondition(productDto, pageable);
        return ResponseEntity.ok(productPage);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@RequestBody @Validated ProductDto productDto, @PathVariable("id") String id) {
        productService.update(productDto, id);
        return ResponseEntity.ok().build();
    }
}
