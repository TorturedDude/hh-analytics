package com.example.restapi.dto.user;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Schema(description = "Request edit user profile")
public class EditUserDto {
    @Schema(description = "Telegram chat id", example = "1234567890987654321")
    @NotNull(message = "Telegram id shouldn't be null")
    Long telegramChatId;
}
