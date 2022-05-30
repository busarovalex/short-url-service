package org.busarovalex.url.shortening.dto;

import org.busarovalex.url.shortening.model.service.HashCalculator;
import org.busarovalex.url.shortening.repository.ShortUrl;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class ShortUrlToDtoConverter implements Converter<ShortUrl, ShortUrlDto> {

    private final HashCalculator hashCalculator;

    public ShortUrlToDtoConverter(HashCalculator hashCalculator) {
        this.hashCalculator = hashCalculator;
    }

    @Override
    public ShortUrlDto convert(ShortUrl source) {
        String hash;
        if (source.getCustomHash() == null) {
            hash = hashCalculator.calculate(source.getId());
        } else {
            hash = source.getCustomHash();
        }
        return new ShortUrlDto(hash, source.getOriginalUrl());
    }
}
