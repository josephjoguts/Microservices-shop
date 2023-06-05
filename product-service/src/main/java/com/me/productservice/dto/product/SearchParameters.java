package com.me.productservice.dto.product;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class SearchParameters {
    private String name;
    private String username;
    private String productPath;
    private Long priceFrom;
    private Long priceTo;
    private Date createdFrom;
    private Date createdTo;
    private List<OptionalProductParam> optionalProductParams;
}
