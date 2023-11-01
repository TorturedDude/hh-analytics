package com.example.restapi.controllers;

import com.example.restapi.dto.user.EditUserDto;
import com.example.restapi.dto.user.GetUserDto;
import com.example.restapi.security.JwtEntity;
import com.example.restapi.services.SecurityService;
import com.example.restapi.services.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
@Tag(name = "User Controller", description = "get and update user")
public class UserController {
    private final UserService userService;
    private final SecurityService securityService;

    @GetMapping(value = {"/"})
    public ResponseEntity<GetUserDto> getUser(Principal principal) {
        JwtEntity jwtPrincipal = securityService.convertPrincipal(principal);

        return new ResponseEntity<>(
                userService.getById(jwtPrincipal.getId()),
                HttpStatus.OK
        );
    }

    @PostMapping(value = {"/"})
    public ResponseEntity<GetUserDto> updateUser(@Validated @RequestBody EditUserDto editUserDto, Principal principal) {
        JwtEntity jwtPrincipal = securityService.convertPrincipal(principal);

        return new ResponseEntity<>(
                userService.update(editUserDto, jwtPrincipal.getId()),
                HttpStatus.OK
        );
    }
}
