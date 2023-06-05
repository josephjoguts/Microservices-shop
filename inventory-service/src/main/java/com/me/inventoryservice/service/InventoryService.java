package com.me.inventoryservice.service;

import com.me.inventoryservice.dto.InventoryAddResponse;
import com.me.inventoryservice.dto.InventoryResponse;
import com.me.inventoryservice.dto.ProductInternal;
import com.me.inventoryservice.model.Inventory;
import com.me.inventoryservice.reposiroty.InventoryRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class InventoryService {
    @Autowired
    private InventoryRepository inventoryRepository;
    @Transactional(readOnly = true)
    public List<InventoryResponse> isInStock(List<String> productIds) {
        log.info("is in stock codes:{}", productIds);
        List<Inventory> inventories = inventoryRepository.findByProductIdIn(productIds);
        log.info("Found in repository {} ", inventories);
        List<InventoryResponse> result = inventories.stream()
                .map(x -> new InventoryResponse()
                        .setQuantity(x.getQuantity())
                        .setUsername(x.getUsername())
                        .setName(x.getName())
                        .setId(x.getProductId())
                        .setPrice(x.getPrice())
                        .setQuantity(x.getQuantity()))
                .collect(Collectors.toList());
        return result;
    }

    public InventoryAddResponse addProduct(ProductInternal productInternal) {
        Inventory inventory = new Inventory()
                .setProductId(productInternal.getId())
                .setName(productInternal.getName())
                .setPrice(productInternal.getPrice())
                .setQuantity(productInternal.getQuantity())
                .setUsername(productInternal.getUsername());

        Inventory save = inventoryRepository.save(inventory);

        return new InventoryAddResponse().setSuccess(true);
    }
}
