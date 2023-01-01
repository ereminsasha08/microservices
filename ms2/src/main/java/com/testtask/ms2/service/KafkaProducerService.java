package com.testtask.ms2.service;

import com.testtask.ms2.model.Message;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class KafkaProducerService {

    @Value("${kafka.topic.name}")
    private String topic;

    private final KafkaTemplate<String, Message> kafkaTemplate;

    public void sendMessage(Message message) {
        kafkaTemplate.send(topic, UUID.randomUUID().toString(), message);
    }
}
