package com.testtask.ms1.controller;

import com.testtask.ms1.model.Message;
import com.testtask.ms1.repository.MessageRepository;
import com.testtask.ms1.service.StompClientService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.Optional;

@Slf4j
@Controller
public class MessageCycleController {
    @Autowired
    MessageRepository messageRepository;
    @Autowired
    StompClientService stompClientService;


    @GetMapping("/start")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<String> start() throws InterruptedException {
        if (!stompClientService.isSendFlag()) {
            stompClientService.setSendFlag(true);
            stompClientService.send();
        } else {
            return ResponseEntity.of(Optional.of("Sending messages has already started\n"));
        }
        return ResponseEntity.of(Optional.of("Sending message started\n"));
    }

    @GetMapping("/stop")
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<String> stop() throws InterruptedException {
        if (!stompClientService.isSendFlag())
            return ResponseEntity.of(Optional.of("Service don't work\n"));
        String stopMessage = stompClientService.stop();
        return ResponseEntity.of(Optional.of(stopMessage + "\n"));
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public void getMessage(@RequestBody Message message) {
        message.setEnd_timestamp(new Date());
        stompClientService.setWorkTime(System.currentTimeMillis());
        Message savedMessage = messageRepository.save(message);
        log.info("Message save in database " + savedMessage);
    }
}
