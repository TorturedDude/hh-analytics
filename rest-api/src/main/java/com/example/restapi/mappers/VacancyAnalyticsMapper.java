package com.example.restapi.mappers;

import com.example.restapi.dto.analytics.GetQueryDataDto;
import com.example.restapi.dto.analytics.GetQueryHistoryDto;
import com.example.restapi.models.VacancyAnalyticsDataPoint;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Component;
import java.lang.reflect.Type;
import java.util.List;

import static org.modelmapper.config.Configuration.AccessLevel.PRIVATE;

@Component
public class VacancyAnalyticsMapper {
    private final ModelMapper modelMapper;

    public VacancyAnalyticsMapper() {
        this.modelMapper = new ModelMapper();

        this.modelMapper.getConfiguration()
                .setMatchingStrategy(MatchingStrategies.STANDARD)
                .setFieldMatchingEnabled(true)
                .setSkipNullEnabled(true)
                .setFieldAccessLevel(PRIVATE);
    }

    public List<GetQueryDataDto> toQueryDataDto(List<VacancyAnalyticsDataPoint> vacancyAnalyticsDataPointList) {
        Type listType = new TypeToken<List<GetQueryDataDto>>() {
        }.getType();

        return modelMapper.map(vacancyAnalyticsDataPointList, listType);
    }

    public List<GetQueryHistoryDto> toQueryHistoryDto(List<VacancyAnalyticsDataPoint> vacancyAnalyticsDataPointList) {
        Type listType = new TypeToken<List<GetQueryHistoryDto>>() {
        }.getType();

        return modelMapper.map(vacancyAnalyticsDataPointList, listType);
    }
}
