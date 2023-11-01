package com.example.restapi.services;

import com.example.restapi.exceptions.ResourceNotFoundException;
import com.example.restapi.security.JwtEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import java.security.Principal;

@Service
public class SecurityService {
    public JwtEntity convertPrincipal(Principal principal) {
        if (principal instanceof UsernamePasswordAuthenticationToken authenticationToken) {
            return (JwtEntity) authenticationToken.getPrincipal();
        } else {
            throw new ResourceNotFoundException("Unknown principal type");
        }
    }
}
