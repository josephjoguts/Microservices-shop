package com.me.cartservice.controller;

import com.me.cartservice.dto.AddCartItemRequest;
import com.me.cartservice.dto.CartResponse;
import com.me.cartservice.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/cart")
@RequiredArgsConstructor
public class CartController {
    private final CartService cartService;

    @PostMapping
    public ResponseEntity<CartResponse> addToCart(@RequestBody AddCartItemRequest request) {
        CartResponse addCartItemResponse = cartService.addCartItem(request);

        return ResponseEntity.ok(addCartItemResponse);
    }
    @GetMapping
    public ResponseEntity<CartResponse> getCart() {
        CartResponse cart = cartService.getCart();
        return ResponseEntity.ok(cart);
    }
    @DeleteMapping
    public ResponseEntity<String> deleteCart() {
        cartService.deleteCart();
        return ResponseEntity.ok("Deleted");
    }
}
