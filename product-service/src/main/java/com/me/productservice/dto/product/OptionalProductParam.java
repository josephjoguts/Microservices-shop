package com.me.productservice.dto.product;

import lombok.Data;

@Data
public class OptionalProductParam {
    private String additionalParamName;
    private String additionalParamValue;
    private Integer additionalParamFrom;
    private Integer additionalParamTo;
}
