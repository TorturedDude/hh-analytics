package com.example.restapi.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VacancyAnalyticsDataPointDTO {
    private LocalDate date;
    private String query;
    private int vacancy_count;
    private double salary;
}
