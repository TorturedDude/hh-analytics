package com.example.restapi.services;

import com.example.restapi.dto.VacancyAnalyticsDataPointDTO;
import com.example.restapi.models.VacancyAnalyticsDataPoint;
import com.example.restapi.repositories.VacancyAnalyticsDataPointRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class VacancyAnalyticsDataPointService {

    private final VacancyAnalyticsDataPointRepository vacancyAnalyticsDataPointRepository;
    private final ModelMapper modelMapper = new ModelMapper();

    public VacancyAnalyticsDataPointService(
            VacancyAnalyticsDataPointRepository vacancyAnalyticsDataPointRepository) {
        this.vacancyAnalyticsDataPointRepository = vacancyAnalyticsDataPointRepository;
    }

    public void testCreateVacancy(VacancyAnalyticsDataPointDTO vacancyAnalyticsDataPoint) {
        vacancyAnalyticsDataPointRepository.save(
                modelMapper.map(vacancyAnalyticsDataPoint, VacancyAnalyticsDataPoint.class)
        );
    }
}
