package com.me.cartservice.model;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class CartItemModel {
    private String itemId;
    private String itemName;
    private String itemUsername;
    private Integer itemQuantity;
    private Long itemPrice;
}
