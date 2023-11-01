package com.example.telegramnotificationservice.exceptions;

public class TelegramApiQuotaException extends RuntimeException {
    public TelegramApiQuotaException(String message) {
        super(message);
    }
}
