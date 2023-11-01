package com.example.restapi.dto.user;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "Response user profile")
public class GetUserDto {
    @Schema(description = "User name", example = "newUser")
    String username;

    @Schema(description = "Telegram chat id", example = "1234567890987654321")
    Long telegramChatId;
}
