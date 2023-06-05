package com.me.orderservice.dto;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class CartResponse {
    private CartModel cartModel;
    private String errorMessage;
}
