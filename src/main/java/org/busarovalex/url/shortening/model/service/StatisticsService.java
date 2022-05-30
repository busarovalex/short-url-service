package org.busarovalex.url.shortening.model.service;

import org.busarovalex.url.shortening.repository.Statistics;
import org.busarovalex.url.shortening.repository.StatisticsRepository;
import org.springframework.core.convert.converter.Converter;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class StatisticsService {

    private final StatisticsRepository repository;

    private final ShortUrlSearchService searchService;

    public StatisticsService(StatisticsRepository repository, ShortUrlSearchService searchService) {
        this.repository = repository;
        this.searchService = searchService;
    }

    @Transactional
    public void updateClicks(Long shortUrlId) {
        repository.updateClicks(shortUrlId);
    }

    @Transactional(readOnly = true)
    public<T> T findByHash(String hash, Converter<Statistics, T> converter) {
        return searchService.findByHash(hash, shortUrl -> converter.convert(shortUrl.getStatistics()));
    }

    @Scheduled(cron = "0 0 0 * * ?")
    @Transactional
    public void scheduleResetDayClick() {
        repository.resetDayClicks();
    }
}
