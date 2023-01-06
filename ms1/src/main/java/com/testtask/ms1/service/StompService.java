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

@Service
@Slf4j
public class StompService {
    @Autowired
    private StompSession session;
    @Value("${interaction.time}")
    private Integer interactionTime;

    public synchronized void setSendFlag(boolean sendFlag) throws InterruptedException {
        this.sendFlag = sendFlag;
        Thread.sleep(1500);
    }

    @Getter
    private  boolean sendFlag;

    @Async
    public void send(int sessionId) throws InterruptedException {
        log.info("Sending message started");
        long startTime = System.currentTimeMillis();
        long endTime = startTime;
        while (sendFlag && interactionTime > (endTime - startTime) / 1000) {
            Message message = new Message(sessionId, new Date());
            log.debug("Send message" + message);
            session.send("/app/process-message", message);
            Thread.sleep(500);
            endTime = System.currentTimeMillis();
        }
        sendFlag = false;
    }

    public void stop() throws InterruptedException {
        this.setSendFlag(false);
        log.info("Sending message stopped.");
    }


}
