package com.example.restapi.dto.user;

import com.example.restapi.dto.validation.OnCreate;
import com.example.restapi.dto.validation.OnUpdate;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;
@Data
@Schema(description = "Request for registration")
public class UserCreateDto {
    @Schema(description = "User name", example = "newUser")
    @NotNull(message = "Username must be not null", groups = {OnCreate.class, OnUpdate.class})
    @Length(min = 3, max = 50, message = "Username length must be between 3 and 50 characters", groups = {OnUpdate.class, OnCreate.class})
    private String username;

    @Schema(description = "User password", example = "123456")
    @NotNull(message = "Password must be not null", groups = {OnCreate.class, OnUpdate.class})
    @Length(min = 6, max = 50, message = "Password length must be between 6 and 50 characters", groups = {OnUpdate.class, OnCreate.class})
    private String password;
}
