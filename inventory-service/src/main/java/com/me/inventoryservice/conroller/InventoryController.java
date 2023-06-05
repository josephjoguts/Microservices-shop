package com.me.inventoryservice.conroller;

import com.me.inventoryservice.dto.InventoryAddResponse;
import com.me.inventoryservice.dto.InventoryResponse;
import com.me.inventoryservice.dto.ProductInternal;
import com.me.inventoryservice.service.InventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/internal/inventory")
public class  InventoryController {
    @Autowired
    private InventoryService inventoryService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<InventoryResponse> isInStock(@RequestParam List<String> productIds) {
        return inventoryService.isInStock(productIds);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public InventoryAddResponse addProduct(@RequestBody ProductInternal productInternal) {
        return inventoryService.addProduct(productInternal);
    }


}
