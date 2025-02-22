package com.webapp.module.media.repository;

import com.webapp.model.Media;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MediaRepository extends JpaRepository<Media, String> {
    
    List<Media> findAllByPostId(Long postId);
}
