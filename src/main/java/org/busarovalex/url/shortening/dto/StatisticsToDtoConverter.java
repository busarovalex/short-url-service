package org.busarovalex.url.shortening.dto;

import org.busarovalex.url.shortening.model.service.HashCalculator;
import org.busarovalex.url.shortening.repository.Statistics;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class StatisticsToDtoConverter implements Converter<Statistics, StatisticsDto> {

    private final HashCalculator hashCalculator;

    public StatisticsToDtoConverter(HashCalculator hashCalculator) {
        this.hashCalculator = hashCalculator;
    }


    @Override
    public StatisticsDto convert(Statistics source) {
        return new StatisticsDto(
                hashCalculator.calculate(source.getShortUrl().getId()),
                source.getDayClicks()
        );
    }
}
