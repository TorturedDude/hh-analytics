package com.example.telegramnotificationservice.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@Slf4j
public class QuotaService {

    @Value("${quota.url}")
    private String url;

    private final RestTemplate restTemplate = new RestTemplate();

    public void requestQuota() {
        log.trace("Requesting to rate limiter");

        long nanoTime = System.nanoTime();
        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
        long elapsedTimeMs = (System.nanoTime() - nanoTime) / 1000000;

        log.debug("Quota retrieved in " + elapsedTimeMs + " ms");

        if (response.getStatusCode() != HttpStatus.OK) {
            log.warn("Rate limiter returned " + response.getStatusCode() + " status code");
            throw new RuntimeException("Rate limiter returned " + response.getStatusCode() + " status code");
        }
    }
}