package com.example.restapi.dto.auth;

import lombok.Data;

@Data
public class JwtResponse {
    private int id;
    private String username;
    private String accessToken;
    private String refreshToken;
}
