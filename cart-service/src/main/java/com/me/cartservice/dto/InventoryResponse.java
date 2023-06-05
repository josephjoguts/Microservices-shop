package com.me.cartservice.dto;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class InventoryResponse {
    private String id;
    private String username;
    private String name;
    private Long price;
    private Integer quantity;
}
