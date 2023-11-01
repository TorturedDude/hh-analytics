package com.example.schedulerservice.rabbitmq;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Slf4j
public class Config {
    @Value("${rabbitmq.exchange.name}")
    private String exchange;

    @Bean
    public MessageConverter converter() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);

        return new Jackson2JsonMessageConverter(mapper);
    }

    @Bean
    public RabbitTemplate template(ConnectionFactory connectionFactory) {
        RabbitTemplate template = new RabbitTemplate(connectionFactory);
        template.setExchange(exchange);
        template.setMessageConverter(converter());

        validateConnection(template);

        return template;
    }

    private void validateConnection(RabbitTemplate template) {
        try {
            log.info("Sending an un-routable message to test RabbitMQ connection");

            template.convertAndSend("Fake message", "Fake queue");
        } catch (Exception e) {
            log.error("Sending failed, rethrowing an exception to crash the service", e);
            throw e;
        }
    }
}
