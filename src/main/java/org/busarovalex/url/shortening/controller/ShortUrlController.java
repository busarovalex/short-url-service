package org.busarovalex.url.shortening.controller;

import org.busarovalex.url.shortening.dto.ShortUrlDto;
import org.busarovalex.url.shortening.dto.StatisticsDto;
import org.busarovalex.url.shortening.model.service.ShortUrlService;
import org.busarovalex.url.shortening.model.service.StatisticsService;
import org.busarovalex.url.shortening.repository.ShortUrl;
import org.busarovalex.url.shortening.repository.Statistics;
import org.springframework.core.convert.converter.Converter;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/")
public class ShortUrlController extends BaseController {

    private final ShortUrlService service;
    private final StatisticsService statisticsService;
    private final Converter<ShortUrl, ShortUrlDto> converter;
    private final Converter<Statistics, StatisticsDto> statisticsConverter;

    public ShortUrlController(
            ShortUrlService service,
            StatisticsService statisticsService,
            Converter<ShortUrl, ShortUrlDto> converter,
            Converter<Statistics, StatisticsDto> statisticsConverter) {
        this.service = service;
        this.statisticsService = statisticsService;
        this.converter = converter;
        this.statisticsConverter = statisticsConverter;
    }

    @PostMapping(consumes = "application/json")
    public ShortUrlDto create(@RequestBody ShortUrlDto dto) {
        ShortUrl newShortUrl = new ShortUrl(dto.getOriginalUrl(), dto.getHash());
        return service.create(newShortUrl, converter);
    }

    @GetMapping(value = "/{hash}")
    public ResponseEntity<ShortUrlDto> findByHash(@PathVariable("hash") String hash) {
        ShortUrlDto dto = service.findByHash(hash, converter);
        return ResponseEntity
                .status(308)
                .header("Location", dto.getOriginalUrl())
                .body(dto);
    }

    @GetMapping(value = "/{hash}/statistics")
    public StatisticsDto statistics(@PathVariable("hash") String hash) {
        return statisticsService.findByHash(hash, statisticsConverter);
    }
}
