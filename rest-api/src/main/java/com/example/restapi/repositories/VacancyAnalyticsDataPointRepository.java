package com.example.restapi.repositories;

import com.example.restapi.models.VacancyAnalyticsDataPoint;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface VacancyAnalyticsDataPointRepository extends CrudRepository<VacancyAnalyticsDataPoint, Integer> {
    List<VacancyAnalyticsDataPoint> findVacancyAnalyticsDataPointByDate(LocalDate date);

    List<VacancyAnalyticsDataPoint> findVacancyAnalyticsDataPointByQueryAndDateBetween(
            String query,
            LocalDate dateStart,
            LocalDate dateEnd,
            Sort sort
    );

    Optional<VacancyAnalyticsDataPoint> findFirstByOrderByDateDesc();
}
