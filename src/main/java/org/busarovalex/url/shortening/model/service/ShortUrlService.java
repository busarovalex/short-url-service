package org.busarovalex.url.shortening.model.service;

import org.busarovalex.url.shortening.repository.ShortUrl;
import org.busarovalex.url.shortening.repository.ShortUrlRepository;
import org.springframework.core.convert.converter.Converter;
import org.springframework.core.task.TaskExecutor;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class ShortUrlService {

    private final HashCalculator hashCalculator;

    private final ShortUrlRepository repository;

    private final ShortUrlValidator validator;

    private final TaskExecutor taskExecutor;

    private final StatisticsService statisticsService;

    private final ShortUrlSearchService searchService;

    public ShortUrlService(HashCalculator hashCalculator,
                           ShortUrlRepository repository,
                           ShortUrlValidator validator,
                           TaskExecutor taskExecutor,
                           StatisticsService statisticsService,
                           ShortUrlSearchService searchService) {
        this.hashCalculator = hashCalculator;
        this.repository = repository;
        this.validator = validator;
        this.taskExecutor = taskExecutor;
        this.statisticsService = statisticsService;
        this.searchService = searchService;
    }

    @Transactional
    public<T> T create(ShortUrl newShortUrl, Converter<ShortUrl, T> converter) {
        validator.validate(newShortUrl);
        repository.save(newShortUrl);
        newShortUrl = checkCustomHashCollisionAndRetry(newShortUrl);
        return converter.convert(newShortUrl);
    }

    public<T> T findByHash(String hash, Converter<ShortUrl, T> converter) {
        Pair<Long, T> idAndDto = searchService
                .findByHash(hash, shortUrl -> Pair.of(shortUrl.getId(), converter.convert(shortUrl)));
        taskExecutor.execute(() -> statisticsService.updateClicks(idAndDto.getFirst()));
        return idAndDto.getSecond();
    }

    private ShortUrl checkCustomHashCollisionAndRetry(ShortUrl newShortUrl) {
        if (newShortUrl.getCustomHash() != null) {
            return newShortUrl;
        }
        while (repository.existsByCustomHash(hashCalculator.calculate(newShortUrl.getId()))) {
            newShortUrl = repository.save(new ShortUrl(
                    newShortUrl.getOriginalUrl()
            ));
        }
        return newShortUrl;
    }
}
