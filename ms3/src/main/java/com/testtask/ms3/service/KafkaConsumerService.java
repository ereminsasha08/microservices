package com.testtask.ms3.service;

import com.testtask.ms3.model.Message;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import java.net.URISyntaxException;
import java.util.Date;

@Service
@Slf4j
public class KafkaConsumerService {

    @Autowired
    private HttpMessageSendService httpMessageSendService;

    @KafkaListener(topics = "${kafka.topic.name}", groupId = "${kafka.group.id}")
    public void listen(@Payload Message message) throws URISyntaxException {
        message.setMc3_timestamp(new Date());
        log.info(String.valueOf(message));
        httpMessageSendService.send(message);
    }
}
