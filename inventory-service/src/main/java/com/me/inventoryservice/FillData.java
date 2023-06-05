package com.me.inventoryservice;


import com.me.inventoryservice.model.Inventory;
import com.me.inventoryservice.reposiroty.InventoryRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class FillData implements CommandLineRunner {
    @Autowired
    private InventoryRepository inventoryRepository;

    @Override
    public void run(String... args) throws Exception {
//        Inventory inventory = new Inventory();
//        inventory.setSkuCode("1");
//        inventory.setQuantity(10);
//        inventoryRepository.save(inventory);
//        log.info("Item saved {}", inventory.getSkuCode());
//
//        Inventory item = new Inventory();
//        item.setSkuCode("2");
//        item.setQuantity(1);
//        inventoryRepository.save(item);
//        log.info("Item saved {}", inventory.getSkuCode());

    }
}
