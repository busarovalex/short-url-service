package org.busarovalex.url.shortening.repository;

import javax.persistence.*;

@Entity
@Table(name = "short_url")
public class ShortUrl {
    @GeneratedValue
    @Id
    private Long id;

    @Column(name = "custom_hash", unique = true)
    private String customHash;

    @Column(name = "original_url", nullable = false)
    private String originalUrl;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "shortUrl")
    private Statistics statistics;

    public ShortUrl() {
    }

    public ShortUrl(String originalUrl, String customHash) {
        this.customHash = customHash;
        this.originalUrl = originalUrl;
        this.statistics = new Statistics(this);
    }

    public ShortUrl(String originalUrl) {
        this.originalUrl = originalUrl;
        this.statistics = new Statistics(this);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCustomHash() {
        return customHash;
    }

    public void setCustomHash(String customHash) {
        this.customHash = customHash;
    }

    public String getOriginalUrl() {
        return originalUrl;
    }

    public void setOriginalUrl(String originalUrl) {
        this.originalUrl = originalUrl;
    }

    public Statistics getStatistics() {
        return statistics;
    }

    public void setStatistics(Statistics statistics) {
        this.statistics = statistics;
    }
}
