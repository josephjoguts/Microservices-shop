package com.me.cartservice.dto;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@Accessors(chain = true)
public class GetCartResponse {
    private List<CartItem> items;
}
