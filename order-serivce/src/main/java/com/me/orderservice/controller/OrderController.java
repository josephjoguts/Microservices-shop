package com.me.orderservice.controller;


import com.me.orderservice.dto.OrderRequest;
import com.me.orderservice.dto.OrderStatusResponse;
import com.me.orderservice.dto.PlaceOrderResponse;
import com.me.orderservice.service.OrderService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.timelimiter.annotation.TimeLimiter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/api/order")
public class OrderController {
    @Autowired
    private OrderService orderService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PlaceOrderResponse placeOrder() {
        return orderService.placeOrder();
    }

    @GetMapping
    private OrderStatusResponse getOrderStatus () {
        return orderService.getOrderStatus();
    }

    public String fallbackPlaceOrder(OrderRequest orderRequest, RuntimeException e) {
        return  "oops something went wrong";
    }
}
