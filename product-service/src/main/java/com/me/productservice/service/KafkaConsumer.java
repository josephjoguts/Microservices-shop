package com.me.productservice.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.me.productservice.event.OrderPlacedInventoryEvent;
import com.me.productservice.model.Product;
import com.me.productservice.repository.ProductRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class KafkaConsumer {
    @Autowired
    private ProductRepository productRepository;

    @KafkaListener(topics = "product-topic", groupId = "group_1", containerFactory = "kafkaListenerContainerFactory")
    public void productTopic(OrderPlacedInventoryEvent message) throws JsonProcessingException {
        log.info("Get items from topic {}", message);

        message.getItems().forEach(x -> {
            Product byId = productRepository.findById(x.getItemId()).orElse(null);
            byId.setQuantity(byId.getQuantity() - x.getQuantity());
            productRepository.save(byId);
        });
    }

}
