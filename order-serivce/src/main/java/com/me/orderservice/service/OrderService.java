package com.me.orderservice.service;

import com.me.orderservice.dto.*;
import com.me.orderservice.event.OrderPlacedInventoryEvent;
import com.me.orderservice.event.OrderPlacedLogisticEvent;
import com.me.orderservice.model.Order;
import com.me.orderservice.model.OrderLineItems;
import com.me.orderservice.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

import static com.me.orderservice.security.Securities.currentUser;

@Service
@Slf4j
@Transactional
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final WebClient.Builder webClientBuilder;
    private final KafkaTemplate<String, OrderPlacedInventoryEvent> kafkaTemplateInventory;
    private final KafkaTemplate<String, OrderPlacedLogisticEvent> kafkaTemplateLogistic;


    public PlaceOrderResponse placeOrder() {
        CartResponse cart = webClientBuilder.baseUrl("http://cart-service").build()
                .get()
                .uri("/api/v1/cart")
                .header("USER", currentUser().getUsername())
                .retrieve()
                .bodyToMono(CartResponse.class)
                .block();

        if (cart == null || CollectionUtils.isEmpty(cart.getCartModel().getItems())) {
            return new PlaceOrderResponse().setErrorMessage("You don't have a cart");
        }

        List<CartItemModel> cartItems = cart.getCartModel().getItems();
        List<String> itemIds = cartItems.stream().map(CartItemModel::getItemId).toList();
        InventoryResponse[] inventoryResponse = webClientBuilder.baseUrl("http://inventory-service").build()
                .get()
                .uri("/api/v1/internal/inventory", uriBuilder -> uriBuilder.queryParam("productIds", itemIds).build())
                .header("USER", currentUser().getUsername())
                .retrieve()
                .bodyToMono(InventoryResponse[].class)
                .block();
        if (inventoryResponse == null || CollectionUtils.isEmpty(Arrays.asList(inventoryResponse))) {
            return new PlaceOrderResponse().setErrorMessage("We don't your items in inventory");
        }
        List<InventoryResponse> inventory = Arrays.asList(inventoryResponse);
        AtomicBoolean isNotInStock = new AtomicBoolean(false);
        cartItems.forEach(cartItem -> {
            InventoryResponse foundItem = inventory.stream()
                    .filter(inventoryItem -> inventoryItem.getId().equals(cartItem.getItemId()))
                    .findFirst().orElse(new InventoryResponse().setId(cartItem.getItemId()).setQuantity(0));
            isNotInStock.set(foundItem.getQuantity() < cartItem.getItemQuantity());
        }
        );
        if (isNotInStock.get()) {
            return new PlaceOrderResponse().setErrorMessage("One of item is not in stock. Validate cart and try again");
        }
        Order order = new Order();
        order.setOrderNumber(UUID.randomUUID().toString());
        order.setUsername(currentUser().getUsername());
        order.setOrderLineItemsList(cartItems.stream().map(
                x -> new OrderLineItems()
                        .setItemUsername(x.getItemUsername())
                        .setItemQuantity(x.getItemQuantity())
                        .setItemName(x.getItemName())
                        .setItemPrice(x.getItemPrice())
                        .setItemId(x.getItemId())
        ).collect(Collectors.toList()));
        Order saved = orderRepository.save(order);
        OrderPlacedInventoryEvent inventoryEvent = new OrderPlacedInventoryEvent()
                .setItems(saved.getOrderLineItemsList().stream().map(
                        x -> new OrderPlacedInventoryEvent.InventoryItem()
                                .setQuantity(x.getItemQuantity())
                                .setItemId(x.getItemId())
                ).toList());
        kafkaTemplateInventory.send("product-topic", inventoryEvent);
        kafkaTemplateInventory.send("inventory-topic", inventoryEvent);

        OrderPlacedLogisticEvent logisticEvent = new OrderPlacedLogisticEvent()
                .setUsername(currentUser().getUsername())
                .setItems(saved.getOrderLineItemsList().stream().map(
                        x -> new OrderPlacedLogisticEvent.LogisticItem()
                                .setQuantity(x.getItemQuantity())
                                .setItemId(x.getItemId())
                                .setItemUsername(x.getItemUsername())
                                .setItemName(x.getItemName())
                ).toList());

        kafkaTemplateLogistic.send("logistic-topic", logisticEvent);

        String message = webClientBuilder.baseUrl("http://cart-service").build()
                .delete()
                .uri("/api/v1/cart")
                .header("USER", currentUser().getUsername())
                .retrieve()
                .bodyToMono(String.class)
                .block();
        return new PlaceOrderResponse().setOrder(saved);
    }

    public OrderStatusResponse getOrderStatus() {
        List<Order> orders = orderRepository.getOrderByUsername(currentUser().getUsername());
        return new OrderStatusResponse().setOrders(orders);
    }
}
