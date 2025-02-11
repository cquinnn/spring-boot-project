package com.webapp.user.entity;

import com.webapp.auth.entity.Role;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
public class User {
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  String id;

  @Column(
      name = "username",
      unique = true,
      columnDefinition = "VARCHAR(255) COLLATE utf8mb4_unicode_ci")
  String username;

  String password;
  String firstName;
  String lastName;
  LocalDate dob;

  @ManyToMany
  List<Role> roles;

}
