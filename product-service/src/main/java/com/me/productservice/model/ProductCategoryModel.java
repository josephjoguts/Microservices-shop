package com.me.productservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(value = "category")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Accessors(chain = true)
public class ProductCategoryModel {
    @Id
    private String id;
    private String username;
    private String categoryName;
    private List<SubCategoryModel> subCategories;
    private List<CategoryOptionModel> categoryOptionModels;
}
