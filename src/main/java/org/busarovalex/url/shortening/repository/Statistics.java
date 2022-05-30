package org.busarovalex.url.shortening.repository;

import javax.persistence.*;

@Entity
@Table(name = "statistics")
public class Statistics {
    @Id
    @GeneratedValue
    private Long id;

    @OneToOne
    @JoinColumn(name = "short_url_id", referencedColumnName = "id")
    private ShortUrl shortUrl;

    @Column(name = "day_clicks", nullable = false)
    private Long dayClicks;

    public Statistics() {
    }

    public Statistics(ShortUrl shortUrl) {
        this.shortUrl = shortUrl;
        dayClicks = 0L;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ShortUrl getShortUrl() {
        return shortUrl;
    }

    public void setShortUrl(ShortUrl shortUrl) {
        this.shortUrl = shortUrl;
    }

    public Long getDayClicks() {
        return dayClicks;
    }

    public void setDayClicks(Long dayClicks) {
        this.dayClicks = dayClicks;
    }
}
