package com.me.productservice.dto.category;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class CategoryOption {
    //todo Possible  multiple options
    private String name;
    private Long min;
    private Long max;
    private boolean required;
}
