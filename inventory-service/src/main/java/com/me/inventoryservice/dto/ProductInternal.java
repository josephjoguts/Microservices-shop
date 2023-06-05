package com.me.inventoryservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.Id;

import java.util.Date;
import java.util.Map;

@Data
@Accessors(chain = true)
public class ProductInternal {
    private String id;
    private String username;
    private String name;
    private Long price;
    private Integer quantity;
}
