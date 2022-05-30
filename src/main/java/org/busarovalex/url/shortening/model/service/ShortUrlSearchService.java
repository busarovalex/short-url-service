package org.busarovalex.url.shortening.model.service;

import org.busarovalex.url.shortening.model.service.exception.HashNotFoundException;
import org.busarovalex.url.shortening.repository.ShortUrl;
import org.busarovalex.url.shortening.repository.ShortUrlRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Component
class ShortUrlSearchService {

    private final HashCalculator hashCalculator;

    private final ShortUrlRepository repository;

    private final ShortUrlValidator validator;

    public ShortUrlSearchService(HashCalculator hashCalculator,
                                 ShortUrlRepository repository,
                                 ShortUrlValidator validator) {
        this.hashCalculator = hashCalculator;
        this.repository = repository;
        this.validator = validator;
    }

    @Transactional(readOnly = true)
    <T> T findByHash(String hash, Converter<ShortUrl, T> converter) {
        validator.validateHash(hash);
        return findByHash(hash).map(converter::convert).orElseThrow(HashNotFoundException::new);
    }

    private Optional<ShortUrl> findByHash(String hash) {
        return repository.findByCustomHash(hash)
                .or(() -> repository.findById(hashCalculator.calculate(hash)));
    }
}
