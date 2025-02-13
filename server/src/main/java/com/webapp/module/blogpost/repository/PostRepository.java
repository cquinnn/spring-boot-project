package com.webapp.module.blogpost.repository;

import com.webapp.model.Post;
import com.webapp.module.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    boolean existsByTitle(String title);

    Optional<Post> findByTitle(String title);

    List<Post> findByAuthor(User author);

}
