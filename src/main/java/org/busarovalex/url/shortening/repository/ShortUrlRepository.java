package org.busarovalex.url.shortening.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ShortUrlRepository extends CrudRepository<ShortUrl, Long> {

    Optional<ShortUrl> findByCustomHash(String customHash);

    boolean existsById(Long id);

    boolean existsByCustomHash(String customHash);

}
