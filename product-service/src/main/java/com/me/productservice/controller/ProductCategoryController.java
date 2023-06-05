package com.me.productservice.controller;

import com.me.productservice.dto.category.ProductCategoryCreateRequest;
import com.me.productservice.dto.category.ProductCategoryResponse;
import com.me.productservice.service.ProductCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/product/category")
public class ProductCategoryController {
    @Autowired
    private ProductCategoryService productCategoryService;

    @PostMapping
    public ResponseEntity<String> create(@RequestBody ProductCategoryCreateRequest request) {
        productCategoryService.create(request);
        //TODO better response
        return ResponseEntity.ok("created");
    }
    @GetMapping("/{username}")
    public ResponseEntity<ProductCategoryResponse> getUserCategories(@PathVariable(name = "username") String username) {
        return ResponseEntity.ok(productCategoryService.getUserCategories(username));

    }
}
