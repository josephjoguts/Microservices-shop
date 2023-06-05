package com.me.productservice.dto.category;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@Accessors(chain = true)
public class ProductCategoryCreateRequest {
    private List<SubCategory> categories;
}
