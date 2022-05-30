package org.busarovalex.url.shortening.repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StatisticsRepository extends CrudRepository<Statistics, Long> {

    @Modifying
    @Query("UPDATE Statistics s SET s.dayClicks = s.dayClicks + 1 where s.shortUrl.id=:shortUrlId")
    void updateClicks(Long shortUrlId);

    @Modifying
    @Query("UPDATE Statistics s SET s.dayClicks = 0")
    void resetDayClicks();
}
