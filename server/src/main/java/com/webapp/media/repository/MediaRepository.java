package com.webapp.media.repository;

import com.webapp.media.entity.Media;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MediaRepository extends JpaRepository<Media, String> {
    boolean existsByTitle(String title);

    Optional<Media> findByTitle(String title);

}
