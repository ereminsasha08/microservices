package com.testtask.ms3.service;

import com.testtask.ms3.model.Message;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;

@Service
public class HttpMessageSendService {

    @Value("${microservice1}")
    String urlMicroservice1;
    private RestTemplate restTemplate;

    public HttpMessageSendService() {
        restTemplate = new RestTemplate();
    }

    public void send(Message message) throws URISyntaxException {
        RequestEntity<?> requestEntity = new RequestEntity<>(message, HttpMethod.
                POST, new URI(urlMicroservice1));
        ResponseEntity<?> response = restTemplate.exchange(requestEntity, new
                ParameterizedTypeReference<Message>() {
                });
    }
}
