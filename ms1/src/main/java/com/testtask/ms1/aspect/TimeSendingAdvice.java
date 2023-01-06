package com.testtask.ms1.aspect;

import com.testtask.ms1.repository.MessageRepository;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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
    public void aroundSendMessage(ProceedingJoinPoint joinPoint) {
        long startSending = System.currentTimeMillis();
        try {
            joinPoint.proceed();
        } catch (Throwable e) {
            log.info("Error sending message");
        }
        long endSending = System.currentTimeMillis();
        long workTime = (endSending - startSending) / 1000;
        int countMessage = messageRepository.countMessagesFromLastSession().orElse(1);
        log.info("Total messages send: {}. Time work: {}s", countMessage, workTime);
    }

}
