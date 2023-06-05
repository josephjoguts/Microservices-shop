package com.me.inventoryservice.dto;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class InventoryAddResponse {
    boolean success;
}
