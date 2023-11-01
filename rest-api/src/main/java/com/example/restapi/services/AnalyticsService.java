package com.example.restapi.services;

import com.example.restapi.dto.analytics.GetQueryDataDto;
import com.example.restapi.dto.analytics.GetQueryHistoryDto;
import com.example.restapi.mappers.VacancyAnalyticsMapper;
import com.example.restapi.models.VacancyAnalyticsDataPoint;
import com.example.restapi.repositories.VacancyAnalyticsDataPointRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AnalyticsService {

    private final VacancyAnalyticsDataPointRepository vacancyRepository;
    private final VacancyAnalyticsMapper vacancyAnalyticsMapper;

    public List<GetQueryDataDto> byQuery() {
        Optional<LocalDate> maxDate = getMaxDate();

        if (maxDate.isEmpty()) {
            return Collections.emptyList();
        }

        return vacancyAnalyticsMapper.toQueryDataDto(
                vacancyRepository.findVacancyAnalyticsDataPointByDate(maxDate.get())
        );
    }

    private Optional<LocalDate> getMaxDate() {
        return vacancyRepository
                .findFirstByOrderByDateDesc()
                .map(VacancyAnalyticsDataPoint::getDate);
    }

    public List<GetQueryHistoryDto> historyQuery(String query, int depth) {
        LocalDate endDate = getMaxDate().orElse(LocalDate.now());
        LocalDate startDate = endDate.minusDays(depth);

        return vacancyAnalyticsMapper.toQueryHistoryDto(
                vacancyRepository.findVacancyAnalyticsDataPointByQueryAndDateBetween(
                        query,
                        startDate,
                        endDate,
                        Sort.by("date").ascending()
                )
        );
    }
}
