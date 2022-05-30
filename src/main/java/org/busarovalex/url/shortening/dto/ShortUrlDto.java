package org.busarovalex.url.shortening.dto;

import java.util.Objects;

public class ShortUrlDto {
    private String hash;

    private String originalUrl;

    public ShortUrlDto() {
    }

    public ShortUrlDto(String hash, String originalUrl) {
        this.hash = hash;
        this.originalUrl = originalUrl;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ShortUrlDto that = (ShortUrlDto) o;
        return Objects.equals(hash, that.hash) && Objects.equals(originalUrl, that.originalUrl);
    }

    @Override
    public int hashCode() {
        return Objects.hash(hash, originalUrl);
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public String getOriginalUrl() {
        return originalUrl;
    }

    public void setOriginalUrl(String originalUrl) {
        this.originalUrl = originalUrl;
    }
}
