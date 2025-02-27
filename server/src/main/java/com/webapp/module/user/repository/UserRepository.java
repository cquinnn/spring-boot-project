package com.webapp.module.user.repository;

import com.webapp.module.user.entity.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
  boolean existsByUsername(String username);

  Optional<User> findByUsername(String username);
}
