package com.testtask.ms1.aspect;

import com.testtask.ms1.model.Message;
import com.testtask.ms1.repository.MessageRepository;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Aspect
@Slf4j
public class TimeSendingAdvice {

    @Autowired
    MessageRepository messageRepository;

    @Pointcut(value = "execution( public * com.testtask.ms1.service.StompService.send(..))")
    public void sendMessage() {
    }

    @Around("sendMessage()")
    public void timeWorkAndCountMessage(ProceedingJoinPoint joinPoint) throws InterruptedException {
        try {
            joinPoint.proceed();
        } catch (Throwable e) {
            log.info("Error sending message");
        }

        Thread.sleep(1500);
        List<Message> firstAndLastMessageFromLastSession = messageRepository.findFirstAndLastMessageFromLastSession();
        Message firstMessage = null;
        Message lastMessage = null;
        for (Message m :
                firstAndLastMessageFromLastSession) {
            firstMessage = firstMessage == null? m: firstMessage;
            lastMessage = lastMessage == null? m: lastMessage;
            firstMessage = firstMessage.getId() > m.getId()? m: firstMessage;
            lastMessage = lastMessage.getId() < m.getId()? m: lastMessage;
        }
        int countMessage = Math.abs(lastMessage.getId() - firstMessage.getId()) + 1;
        long workTime = Math.abs(lastMessage.getEnd_timestamp().getTime() - firstMessage.getMc1_timestamp().getTime());
        log.info("Total messages send: {}. Time work: {}ms", countMessage, workTime);
    }

}
