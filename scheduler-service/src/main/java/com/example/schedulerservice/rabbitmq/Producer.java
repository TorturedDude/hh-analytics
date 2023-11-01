package com.example.schedulerservice.rabbitmq;

import com.example.schedulerservice.dto.AnalyticsBuilderServiceTaskDto;
import com.example.schedulerservice.dto.VacancyImportScheduledTaskDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class Producer {

    private final RabbitTemplate template;

    @Value("${rabbitmq.routingKey.vacancyImport}")
    private String vacancyImportKey;

    @Value("${rabbitmq.routingKey.analyticsBuilder}")
    private String analyticsBuilderKey;

    public Producer(RabbitTemplate template) {
        this.template = template;
    }

    public void sendMessage(AnalyticsBuilderServiceTaskDto task) {
        log.debug("Sending message with object -> " + task + " // routingKey -> " + analyticsBuilderKey);
        template.convertAndSend(analyticsBuilderKey, task);
        log.debug("Message sent");
    }

    public void sendMessage(VacancyImportScheduledTaskDto task) {
        log.debug("Sending message with object -> " + task + " // routingKey -> " + vacancyImportKey);
        template.convertAndSend(vacancyImportKey, task);
        log.debug("Message sent");
    }
}
