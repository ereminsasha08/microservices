package com.testtask.MS1.service;

import com.testtask.MS1.model.Message;
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
public class StompClientService {
    @Autowired
    private StompSession session;
    @Value("${interaction-time}")
    private Integer interactionTime;
    @Setter
    @Getter
    private long workTime = System.currentTimeMillis();
    private AtomicInteger sessionId = new AtomicInteger(0);
    @Getter
    private AtomicInteger countMessage;
    @Getter
    @Setter
    private volatile boolean sendFlag;

    @Async
    public void send() throws InterruptedException {
        log.info("Sending message started");
        countMessage = new AtomicInteger(0);
        long startTime = System.currentTimeMillis();
        long endTime = startTime;
        while (sendFlag && interactionTime > (endTime - startTime) / 1000) {
            Message message = new Message();
            message.setSessionId(sessionId.getAndIncrement());
            message.setMc1_timestamp(new Date());
            log.info("Send message" + message);
            session.send("/app/process-message", message);
            countMessage.incrementAndGet();
            Thread.sleep(500);
            endTime = System.currentTimeMillis();
        }
        sendFlag = false;
        Thread.sleep(1000);
        workTime = (workTime - startTime) / 1000;
        log.info("Sending message stopped. Total messages send " + countMessage.get() + " . Time work: " + workTime + "s");

    }

    public String stop() throws InterruptedException {
        sendFlag = false;
        Thread.sleep(1500);
        return "Sending message stopped. Total messages send " + countMessage.get() + ". Time work: " + workTime + "s";
    }
}
