package com.webapp.blogpost.repository;

import com.webapp.blogpost.entity.Post;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends JpaRepository<Post, String> {
  boolean existsByTitle(String title);

  Optional<Post> findByTitle(String title);

}
