package com.me.inventoryservice.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.me.inventoryservice.event.OrderPlacedInventoryEvent;
import com.me.inventoryservice.model.Inventory;
import com.me.inventoryservice.reposiroty.InventoryRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class KafkaConsumer {
    @Autowired
    private InventoryRepository inventoryRepository;

    @KafkaListener(topics = "inventory-topic", groupId = "group_1", containerFactory = "kafkaListenerContainerFactory")
    public void inventoryTopic(OrderPlacedInventoryEvent message) throws JsonProcessingException {
        log.info("Get items from topic {}", message);
        message.getItems().forEach(x -> {
            Inventory byProductId = inventoryRepository.findByProductId(x.getItemId());
            byProductId.setQuantity(byProductId.getQuantity() - x.getQuantity());
            inventoryRepository.save(byProductId);
        });
    }

}
