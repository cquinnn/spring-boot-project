package com.webapp.video.repository;

import com.webapp.video.entity.Video;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VideoRepository extends JpaRepository<Video, String> {
  boolean existsByTitle(String title);

  Optional<Video> findByTitle(String title);
  
  // @Query(nativeQuery = true, value="SELECT title FROM video")
  // List<String> getAllEntryTitles();
}
