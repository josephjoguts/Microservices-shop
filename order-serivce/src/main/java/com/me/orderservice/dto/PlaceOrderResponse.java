package com.me.orderservice.dto;

import com.me.orderservice.model.Order;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class PlaceOrderResponse {
    private String errorMessage;
    private Order order;
    private String username;
}
