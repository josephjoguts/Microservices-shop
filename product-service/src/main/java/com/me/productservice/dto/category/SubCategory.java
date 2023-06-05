package com.me.productservice.dto.category;

import lombok.Data;

import java.util.List;

@Data
public class SubCategory {
    private String categoryName;
    private List<SubCategory> subCategories;
    private List<CategoryOption> categoryOptions;
}
