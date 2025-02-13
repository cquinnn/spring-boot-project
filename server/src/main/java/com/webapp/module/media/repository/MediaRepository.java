package com.webapp.module.media.repository;

import com.webapp.model.Media;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MediaRepository extends JpaRepository<Media, String> {
    boolean existsByTitle(String title);

    Optional<Media> findByTitle(String title);

    List<Media> findAllByPostId(Long postId);
}
