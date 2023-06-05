package com.me.productservice.model;

import com.me.productservice.dto.category.CategoryOption;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@Accessors(chain = true)
public class SubCategoryModel {
    private String categoryName;
    private List<SubCategoryModel> subCategories;
    private List<CategoryOptionModel> categoryOptions;
}
