package com.me.orderservice.dto;

import lombok.Data;
import lombok.experimental.Accessors;
import java.util.List;

@Data
@Accessors(chain = true)
public class CartModel {
    private String id;
    private List<CartItemModel> items;
    private String username;
    private String cartName;
}
