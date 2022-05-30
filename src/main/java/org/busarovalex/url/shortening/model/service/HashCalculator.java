package org.busarovalex.url.shortening.model.service;

import org.busarovalex.url.shortening.model.service.exception.InvalidHashException;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class HashCalculator {

    private static final long BASE = 62L;

    public Long calculate(String hash) {
        return calculateOpt(hash).orElseThrow(() -> new InvalidHashException(hash));
    }

    public Optional<Long> calculateOpt(String hash) {
        long value = 0;
        for (int i = hash.length() - 1; i >= 0; i--) {
            long multiplication = (long) Math.pow(BASE, hash.length() - i - 1);
            char c = hash.charAt(i);
            if (c >= 'a' && c <= 'z') {
                value += (c - 'a') * multiplication;
            } else if (c >= 'A' && c <= 'Z') {
                value += (c - 'A' + 26) * multiplication;
            } else if (c >= '0' && c <= '9') {
                value += (c - '0' + 52) * multiplication;
            } else {
                return Optional.empty();
            }
        }
        return Optional.of(value);
    }

    public String calculate(long number) {
        StringBuilder result = new StringBuilder();
        for (int i = 5; i >= 0; i--) {
            long divisor = (long) Math.pow(BASE, i);
            long quotient = number / divisor;
            if (quotient > BASE) {
                throw new RuntimeException("Invalid number " + number);
            }
            if (quotient < 26) {
                result.append((char) (quotient + 'a'));
            } else if (quotient < 52) {
                result.append((char) (quotient - 26 + 'A'));
            } else {
                result.append((char) (quotient - 52 + '0'));
            }
            number = number % divisor;
        }
        return result.toString();
    }
}
