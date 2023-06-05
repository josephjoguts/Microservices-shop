package com.me.cartservice.service;

import com.me.cartservice.dto.*;
import com.me.cartservice.model.CartItemModel;
import com.me.cartservice.model.CartModel;
import com.me.cartservice.repository.CartRepository;
import com.me.cartservice.security.User;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.me.cartservice.security.Securities.currentUser;

@Service
public class CartService {
    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private WebClient.Builder webClientBuilder;
    public CartResponse addCartItem(AddCartItemRequest request) {
        //todo validation
        User user = currentUser();

        InventoryResponse[] result = webClientBuilder.baseUrl("http://inventory-service").build()
                .get()
                .uri("/api/v1/internal/inventory", uriBuilder -> uriBuilder.queryParam("productIds", List.of(request.getItemId())).build())
                .header("USER", currentUser().getUsername())
                .retrieve()
                .bodyToMono(InventoryResponse[].class)
                .block();
        if (result == null) {
            return new CartResponse().setErrorMessage("Request item not found");
        }
        List<InventoryResponse> list = Arrays.asList(result);
        if (CollectionUtils.isEmpty(list)) {
            return new CartResponse().setErrorMessage("Request item not found");
        }
        if (request.getQuantity() == null) {
            request.setQuantity(1);
        }
        InventoryResponse inventoryResponse = list.get(0);
        CartModel cartModel = cartRepository.findByUsername(user.getUsername())
                .orElse(new CartModel().setUsername(user.getUsername()).setItems(new ArrayList<>()));
        CartItemModel cartItemModel = cartModel.getItems().stream().filter(x -> x.getItemId().equals(request.getItemId())).findFirst().orElse(
                new CartItemModel()
                        .setItemId(request.getItemId())
                        .setItemUsername(inventoryResponse.getUsername())
                        .setItemName(inventoryResponse.getName())
                        .setItemQuantity(0)
                        .setItemPrice(inventoryResponse.getPrice())
        );
        cartItemModel.setItemQuantity(cartItemModel.getItemQuantity() + request.getQuantity());
        if (inventoryResponse.getQuantity() < cartItemModel.getItemQuantity()) {
            return new CartResponse().setErrorMessage("Can't add item because quantity is too much");
        }
        if (cartModel.getItems().stream().noneMatch(x -> x.getItemId().equals(request.getItemId()))) {
            cartModel.getItems().add(cartItemModel);
        }
        CartModel save = cartRepository.save(cartModel);
        return new CartResponse().setCartModel(save);
    }

    @GetMapping
    public CartResponse getCart() {
        //todo validation and check inventory
        User user = currentUser();
        CartModel cartModel = cartRepository.findByUsername(user.getUsername())
                .orElse(new CartModel().setUsername(user.getUsername()).setItems(new ArrayList<>()));

        return new CartResponse().setCartModel(cartModel);
    }

    public void deleteCart() {
        User user = currentUser();
        cartRepository.deleteByUsername(user.getUsername());
    }
}
