package org.busarovalex.url.shortening.model.service;

import org.apache.commons.validator.routines.UrlValidator;
import org.busarovalex.url.shortening.model.service.exception.ValidationException;
import org.busarovalex.url.shortening.repository.ShortUrl;
import org.busarovalex.url.shortening.repository.ShortUrlRepository;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

import static org.apache.logging.log4j.util.Strings.isBlank;
import static org.apache.logging.log4j.util.Strings.isEmpty;

@Component
class ShortUrlValidator {

    private final UrlValidator urlValidator;
    private final ShortUrlRepository repository;
    private final HashCalculator calculator;

    private static final int MAX_HASH_LENGTH = 6;

    private static final String HASH_CHARS = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
    private static final Set<Integer> HASH_CHARS_SET = HASH_CHARS.chars().boxed().collect(Collectors.toSet());

    public ShortUrlValidator(ShortUrlRepository repository, HashCalculator calculator) {
        this.repository = repository;
        this.calculator = calculator;
        urlValidator = new UrlValidator(new String[]{"http", "https"});
    }

    void validate(ShortUrl newShortUrl) {
        String originalUrl = newShortUrl.getOriginalUrl();
        if (originalUrl == null || isEmpty(originalUrl) || isBlank(originalUrl)) {
            throw new ValidationException("originalUrl", "too_short");
        }
        if (!urlValidator.isValid(originalUrl)) {
            throw new ValidationException("originalUrl", "format");
        }
        String customHash = newShortUrl.getCustomHash();
        if (customHash != null) {
            validateHash(customHash);
            if (repository.existsByCustomHash(customHash) ||
                    calculator.calculateOpt(customHash).map(repository::existsById).orElse(false)) {
                throw new ValidationException("hash", "exists");
            }
        }
    }

    void validateHash(String hash) {
        if (hash == null) {
            throw new ValidationException("hash", "required");
        }
        if (hash.length() > MAX_HASH_LENGTH) {
            throw new ValidationException("hash", "too_long");
        }
        if (hash.length() == 0) {
            throw new ValidationException("hash", "too_short");
        }
        if (!hash.chars().allMatch(HASH_CHARS_SET::contains)) {
            throw new ValidationException("hash", "format");
        }
    }
}
