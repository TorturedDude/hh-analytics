package com.example.telegramnotificationservice.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class TelegramNotificationTaskDTO {
    @JsonProperty("chat_id")
    private long chatId;
    private String message;
}