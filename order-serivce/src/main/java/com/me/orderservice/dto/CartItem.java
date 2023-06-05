package com.me.orderservice.dto;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class CartItem {
    private String itemName;
    private String itemUsername;
    private Integer itemQuantity;
}
