package com.me.cartservice.model;

import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(value = "cart")
@Data
@Accessors(chain = true)
public class  CartModel {
    @Id
    private String id;
    private List<CartItemModel> items;
    private String username;
    private String cartName;
}
