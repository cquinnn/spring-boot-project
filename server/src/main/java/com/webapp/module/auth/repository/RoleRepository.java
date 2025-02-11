package com.webapp.module.auth.repository;

import com.webapp.model.Role;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, String> {
  Optional<Role> findByName(String role);
  List<Role> findByNameIn(List<String> names);
}
