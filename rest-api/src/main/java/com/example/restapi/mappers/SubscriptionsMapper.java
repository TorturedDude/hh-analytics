package com.example.restapi.mappers;

import com.example.restapi.dto.subscription.SubscriptionDto;
import com.example.restapi.models.Subscription;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Component;

import java.lang.reflect.Type;
import java.util.List;

import static org.modelmapper.config.Configuration.AccessLevel.PRIVATE;

@Component
public class SubscriptionsMapper {
    private final ModelMapper modelMapper;

    public SubscriptionsMapper() {
        this.modelMapper = new ModelMapper();

        this.modelMapper.getConfiguration()
                .setMatchingStrategy(MatchingStrategies.STANDARD)
                .setFieldMatchingEnabled(true)
                .setSkipNullEnabled(true)
                .setFieldAccessLevel(PRIVATE);
    }

    public SubscriptionDto toDto(Subscription subscription) {
        return modelMapper.map(subscription, SubscriptionDto.class);
    }

    public List<SubscriptionDto> toDtoList(List<Subscription> subscriptions) {
        Type listType = new TypeToken<List<SubscriptionDto>>() {}.getType();
        return modelMapper.map(subscriptions, listType);
    }

}
