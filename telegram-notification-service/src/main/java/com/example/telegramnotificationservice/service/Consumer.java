package com.example.telegramnotificationservice.service;

import com.example.telegramnotificationservice.bot.TelegramBot;
import com.example.telegramnotificationservice.dto.TelegramNotificationTaskDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Service
@Slf4j
public class Consumer {
    private final TelegramBot telegramBot;

    public Consumer(TelegramBot telegramBot) {
        this.telegramBot = telegramBot;
    }

    @RabbitListener(queues = {"${rabbitmq.queue}"})
    public void consume(TelegramNotificationTaskDTO message) {
        try {
            telegramBot.sendMessage(message.getChatId(), message.getMessage());
        } catch (TelegramApiException e) {
            log.error("Error while sending message to chat #" + message.getChatId(), e);
        }
    }
}
