package com.me.orderservice.dto;

import com.me.orderservice.model.Order;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@Accessors(chain = true)
public class OrderStatusResponse {
    private List<Order> orders;
}
