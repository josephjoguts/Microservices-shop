package com.me.logisticservice.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.me.logisticservice.event.OrderPlacedLogisticEvent;
import com.me.logisticservice.model.LogisticModel;
import com.me.logisticservice.repository.LogisticRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class KafkaConsumer {
    @Autowired
    private LogisticRepository logisticRepository;

    @KafkaListener(topics = "logistic-topic", groupId = "group_1", containerFactory = "kafkaListenerContainerFactory")
    public void productTopic(OrderPlacedLogisticEvent message) throws JsonProcessingException {
        log.info("Get items from topic {}", message);

        List<LogisticModel> created = message.getItems().stream().map(
                x -> new LogisticModel()
                        .setUsername(message.getUsername())
                        .setItemName(x.getItemName())
                        .setItemQuantity(x.getQuantity())
                        .setItemUsername(x.getItemUsername())
                        .setStatus("Created")
        ).toList();

        logisticRepository.saveAll(created);
    }
}
