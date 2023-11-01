package com.example.restapi.dto.auth;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Schema(description = "Request for login")
public class JwtRequest {
    @Schema(description = "User name", example = "newUser")
    @NotNull(message = "Username must be not null")
    @NotEmpty(message = "Username must be not empty")
    private String username;

    @Schema(description = "User password", example = "123456")
    @NotNull(message = "Password must be not null")
    @NotEmpty(message = "Password must be not empty")
    private String password;
}
