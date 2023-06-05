package com.me.cartservice.dto;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class CartItem {
    private String itemName;
    private String itemUsername;
    private Integer itemQuantity;
}
