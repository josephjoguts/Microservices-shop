package com.me.productservice.dto.product;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Data
@Accessors(chain = true)
public class ProductListResponse {
    private String id;
    private String name;
    private String description;
    private Long price;
    private Integer quantity;
    private String productPath;
    private Map<String, Object> productOptions;
    private Date createdAt;
    private String username;
}
