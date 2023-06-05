package com.me.productservice.model;

import com.me.productservice.dto.product.ProductOption;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Document(value = "product")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Accessors(chain = true)
public class Product {
    //TODO ADD SYSTEM ID TO TRACK BEETWEN SERVICES
    @Id
    private String id;
    private String username;
    private String name;
    private String description;
    private Long price;
    private Integer quantity;
    private String productPath;
    private Date createdAt;
    private Map<String, Object> productOptions;
}
