package com.me.productservice.dto.product;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.Id;

import java.util.Date;

@Data
@Accessors(chain = true)
public class ProductInternal {
    private String id;
    private String username;
    private String name;
    private Integer quantity;
    private Long price;
}
