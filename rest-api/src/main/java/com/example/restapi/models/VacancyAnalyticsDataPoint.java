package com.example.restapi.models;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDate;

@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
@ToString
@Table(name = "Vacancy_analytics")
public class VacancyAnalyticsDataPoint {

    @Id
    private int id;

    @Column("date")
    private LocalDate date;

    @Column("query")
    private String query;

    @Column("vacancy_count")
    private int vacancyCount;

    @Column("average_salary")
    private double salary;
}