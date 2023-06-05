package com.me.cartservice.dto;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class AddCartItemRequest {
    private String itemId;
    private String itemName;
    private String itemUsername;
    private String cartName;
    private Integer quantity;
}
