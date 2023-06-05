package com.me.productservice.dto.product;

import lombok.Data;
import lombok.experimental.Accessors;


@Data
@Accessors(chain = true)
public class ProductsSearchRequest {
    private Page page;
    private SearchParameters search;
}
