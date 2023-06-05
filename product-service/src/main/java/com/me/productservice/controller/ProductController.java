package com.me.productservice.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.me.productservice.dto.product.ProductCreateRequest;
import com.me.productservice.dto.product.ProductListResponse;
import com.me.productservice.dto.product.ProductsSearchRequest;
import com.me.productservice.service.ProductCategoryService;
import com.me.productservice.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/product")
public class ProductController {
    @Autowired
    private ProductService productService;
    @Autowired
    private ProductCategoryService productCategoryService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProductListResponse createProduct(@RequestBody ProductCreateRequest request) throws JsonProcessingException {
        return productService.createProduct(request);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<ProductListResponse> getProducts(@RequestBody ProductsSearchRequest request) {
        return productService.getProductsFiltered(request);
    }
}
