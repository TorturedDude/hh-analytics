package com.example.restapi.controllers.auth;

import com.example.restapi.dto.user.UserCreateDto;
import com.example.restapi.dto.auth.JwtRequest;
import com.example.restapi.dto.auth.JwtResponse;
import com.example.restapi.dto.validation.OnCreate;
import com.example.restapi.services.AuthService;
import com.example.restapi.services.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@AllArgsConstructor
@Slf4j
@Tag(name = "Auth Controller", description = "registration and login endpoint")
public class AuthController {

    private final UserService userService;
    private final AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<ResponseStatus> singUp(@Validated(OnCreate.class) @RequestBody UserCreateDto userCreateDto) {
        log.debug("Receive userCreateDto:" + userCreateDto);
        userService.createUser(userCreateDto);
        log.debug("User created successfully");
        return ResponseEntity.ok().build();
    }

    @PostMapping("/signin")
    public JwtResponse login(@Validated @RequestBody JwtRequest loginRequest) {
        return authService.login(loginRequest);
    }
}
