package com.testtask.ms1.service;

import com.testtask.ms1.model.Message;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.concurrent.atomic.AtomicInteger;

@Service
@Slf4j
public class StompService {
    @Autowired
    private StompSession session;
    @Value("${interaction.time}")
    private Integer interactionTime;
    @Setter
    @Getter
    private long workTime;
    private AtomicInteger sessionId = new AtomicInteger(0);
    @Getter
    private AtomicInteger countMessage;
    @Getter
    @Setter
    private  boolean sendFlag;

    @Async
    public void send() throws InterruptedException {
        log.info("Sending message started");
        countMessage = new AtomicInteger(0);
        long startTime = System.currentTimeMillis();
        long endTime = startTime;
        while (sendFlag && interactionTime > (endTime - startTime) / 1000) {
            Message message = new Message();
            message.setSessionId(sessionId.get());
            message.setMc1_timestamp(new Date());
            log.info("Send message" + message);
            session.send("/app/process-message", message);
            countMessage.incrementAndGet();
            Thread.sleep(500);
            endTime = System.currentTimeMillis();
        }
        Thread.sleep(1500);
        workTime = (workTime - startTime) / 1000;
        sessionId.incrementAndGet();
        log.info("Sending message stopped. Total messages send: {}. Time work: {}s", countMessage.get(), workTime);
        sendFlag = false;

    }

    public String stop() throws InterruptedException {
        sendFlag = false;
        Thread.sleep(2000);
        return "Sending message stopped. Total messages send: " + countMessage.get() + ". Time work: " + workTime + "s";
    }


}
