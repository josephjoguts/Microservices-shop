package com.me.productservice.event;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@Accessors(chain = true)
public class OrderPlacedInventoryEvent {
    private List<InventoryItem> items;
    @Data
    @Accessors(chain = true)
    public static class InventoryItem {
        private String itemId;
        private Integer quantity;
    }
}
