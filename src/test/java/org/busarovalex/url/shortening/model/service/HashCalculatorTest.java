package org.busarovalex.url.shortening.model.service;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class HashCalculatorTest {

    private final HashCalculator calculator = new HashCalculator();

    @MethodSource("hashToNumber")
    @ParameterizedTest
    void convertsHashToNumber(String hash, Long expectedNumber) {
        Long actualNumber = calculator.calculate(hash);
        assertEquals(expectedNumber, actualNumber);
    }

    @MethodSource("hashToNumber")
    @ParameterizedTest
    void convertsNumberToHash(String expectedHash, Long number) {
        String actualHash = calculator.calculate(number);
        assertEquals(expectedHash, actualHash);
    }

    private static Stream<Arguments> hashToNumber() {
        return Stream.of(
                Arguments.of("aaaaaa", 0L),
                Arguments.of("aaaaab", 1L),
                Arguments.of("aaaaa9", 61L),
                Arguments.of("aaaaba", 62L),
                Arguments.of("aaaa9a", 61L * 62L),
                Arguments.of("baaaaa", (long) Math.pow(62L, 5L))
        );
    }
}
