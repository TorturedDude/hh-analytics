package com.example.restapi.dto;

import lombok.Data;

@Data
public class SubscriptionCreateDto {
    private int userId;

    private String query;
}
