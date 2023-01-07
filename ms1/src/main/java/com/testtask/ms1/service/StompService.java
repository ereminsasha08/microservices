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
    @Getter
    private AtomicInteger countMessage;
    @Getter
    private  boolean sendFlag;

    @Async
    public void send(int sessionId) throws InterruptedException {
        log.info("Sending message started");
        countMessage = new AtomicInteger(0);
        long startTime = System.currentTimeMillis();
        long endTime = startTime;
        while (sendFlag && interactionTime > (endTime - startTime) / 1000) {
            Message message = new Message();
            message.setSessionId(sessionId);
            message.setMc1_timestamp(new Date());
            log.debug("Send message" + message);
            session.send("/app/process-message", message);
            countMessage.incrementAndGet();
            Thread.sleep(500);
            endTime = System.currentTimeMillis();
        }
        Thread.sleep(800);
        workTime = (workTime - startTime) / 1000;
        log.info("Sending message stopped. Total messages send: {}. Time work: {}s", countMessage.get(), workTime);
        sendFlag = false;

    }

    public String stop() throws InterruptedException {
        this.setSendFlag(false);
        return "Sending message stopped. Total messages send: " + countMessage.get() + ". Time work: " + workTime + "s";
    }

    public synchronized void setSendFlag(boolean sendFlag) throws InterruptedException {
        this.sendFlag = sendFlag;
        Thread.sleep(1300);
    }
}
