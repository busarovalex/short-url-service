package org.busarovalex.url.shortening.dto;

public class StatisticsDto {
    private String hash;

    private Long dayClicks;

    public StatisticsDto(String hash, Long dayClicks) {
        this.hash = hash;
        this.dayClicks = dayClicks;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public Long getDayClicks() {
        return dayClicks;
    }

    public void setDayClicks(Long dayClicks) {
        this.dayClicks = dayClicks;
    }
}
