package com.me.productservice.repository;

import com.me.productservice.model.Product;
import com.me.productservice.model.ProductCategoryModel;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductCategoryRepository extends MongoRepository<ProductCategoryModel, String> {
    Optional<List<ProductCategoryModel>> findByUsername(String s);
}
