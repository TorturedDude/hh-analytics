package com.example.restapi.security;

import com.example.restapi.models.User;

public class JwtEntityFactory {
    public static JwtEntity create(User user) {
        return new JwtEntity(
                user.getId(),
                user.getUsername(),
                user.getPassword()
        );
    }

}
