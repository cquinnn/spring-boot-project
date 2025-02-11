package com.webapp.user.dto.response;

import com.webapp.auth.dto.response.RoleResponse;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Value
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserResponse {
  String id;
  String username;
  String firstName;
  String lastName;
  LocalDate dob;
  List<RoleResponse> roles;
}
