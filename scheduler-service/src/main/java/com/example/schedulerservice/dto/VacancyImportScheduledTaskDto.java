package com.example.schedulerservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

@Data
@AllArgsConstructor
@ToString
public class VacancyImportScheduledTaskDto {
    int pageSize;
    int pageNumber;
    String query;
}
