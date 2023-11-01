package com.example.telegramnotificationservice.bot;

import com.example.telegramnotificationservice.exceptions.TelegramApiQuotaException;
import com.example.telegramnotificationservice.service.QuotaService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Component
@Slf4j
public class TelegramBot extends TelegramLongPollingBot {
    @Value("${bot.name}")
    private String name;

    @Value("${bot.site-url}")
    private String siteUrl;

    private final QuotaService quotaService;


    public TelegramBot(@Value("${bot.token}") String botToken, QuotaService quotaService) {
        super(botToken);
        this.quotaService = quotaService;
    }

    @Override
    public void onUpdateReceived(Update update) {
        log.info("Received message from user");

        long chatId = update.getMessage().getChatId();
        String messageText = "Ваш chat id = `"
                + chatId
                + "`\nДля получения уведомлений введите его в форму на странице профиля на сайте " +
                siteUrl;

        SendMessage message = new SendMessage();
        message.enableMarkdown(true);
        message.setChatId(chatId);
        message.setText(messageText);
        try {
            execute(message);
        } catch (TelegramApiException e) {
            log.error("Failed to send message \"{}\"", messageText);
            log.error("Exception", e);
        }
    }

    public void sendMessage(long chatId, String text) throws TelegramApiException {
        try {
            quotaService.requestQuota();
            log.debug("Has quota");

            SendMessage command = new SendMessage();
            command.enableHtml(true);
            command.disableWebPagePreview();
            command.setChatId(chatId);
            command.setText(text);

            this.execute(command);
        } catch (TelegramApiQuotaException e) {
            log.error("We have exceeded the Telegram API quotas by swallowing the exception so the message will not be sent to the user");
        } catch (TelegramApiException e) {
            log.error("Failed to send message \"{}\"", text);
            log.error("Exception", e);

            log.error("Unknown exception occurred");
            throw e;
        }
    }

    @Override
    public String getBotUsername() {
        return name;
    }
}