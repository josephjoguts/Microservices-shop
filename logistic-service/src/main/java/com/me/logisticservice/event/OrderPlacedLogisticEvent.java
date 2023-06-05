package com.me.logisticservice.event;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@Accessors(chain = true)
public class OrderPlacedLogisticEvent {
    private String username;
    private List<LogisticItem> items;
    @Data
    @Accessors(chain = true)
    public static class LogisticItem {
        private String itemId;
        private String itemName;
        private String itemUsername;
        private Integer quantity;
    }
}
