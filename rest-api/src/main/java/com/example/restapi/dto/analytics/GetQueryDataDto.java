package com.example.restapi.dto.analytics;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetQueryDataDto {
    private String query;
    private int vacancyCount;
    private double salary;
}
