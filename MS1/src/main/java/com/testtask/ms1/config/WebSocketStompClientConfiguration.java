package com.testtask.MS1.config;

import com.testtask.MS1.util.ClientOneSessionHandler;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.web.socket.client.WebSocketClient;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;

import java.util.concurrent.ExecutionException;

@Configuration
public class WebSocketStompClientConfiguration {

    @Value("${microservice2}")
    String microservice2;


    @Bean
    public StompSession stompSession() throws ExecutionException, InterruptedException {
        WebSocketClient client = new StandardWebSocketClient();
        WebSocketStompClient stompClient = new WebSocketStompClient(client);
        stompClient.setMessageConverter(new MappingJackson2MessageConverter());

        ClientOneSessionHandler clientOneSessionHandler = new ClientOneSessionHandler();
        ListenableFuture<StompSession> sessionAsync =
                stompClient.connect(microservice2, clientOneSessionHandler);
        StompSession session = sessionAsync.get();
        session.subscribe("/topic/messages", clientOneSessionHandler);
        return session;
    }

}
