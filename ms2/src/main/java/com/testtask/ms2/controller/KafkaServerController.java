package com.testtask.ms2.controller;


import com.testtask.ms2.model.Message;
import com.testtask.ms2.service.KafkaProducerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import java.util.Date;

@Controller
@Slf4j
public class KafkaServerController {
    @Autowired
    private KafkaProducerService producerController;

    @MessageMapping("/process-message")
    @SendTo("/topic/messages")
    public Message processMessage(Message message) {
        message.setMc2_timestamp(new Date());
        log.info(String.valueOf(message));
        producerController.sendMessage(message);
        return message;
    }
}
