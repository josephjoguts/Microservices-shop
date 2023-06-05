package com.me.cartservice.dto;

import com.me.cartservice.model.CartModel;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class CartResponse {
    private CartModel cartModel;
    private String errorMessage;
}
