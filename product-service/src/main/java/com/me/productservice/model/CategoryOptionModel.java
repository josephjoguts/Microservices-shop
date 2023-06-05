package com.me.productservice.model;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class CategoryOptionModel {
    private String name;
    private Long min;
    private Long max;
    private boolean required;
}
