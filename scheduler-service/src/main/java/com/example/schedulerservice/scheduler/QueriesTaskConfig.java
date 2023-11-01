package com.example.schedulerservice.scheduler;

import jakarta.annotation.PostConstruct;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Data
@ConfigurationProperties(prefix = "application")
@Slf4j
public class QueriesTaskConfig {
    private List<String> queries;

    @PostConstruct
    private void logQueries() {
        log.info("Queries initialized - " + queries);
    }
}
