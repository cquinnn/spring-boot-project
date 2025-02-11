package com.webapp.module.user.dto.response;

import com.webapp.module.auth.dto.response.RoleResponse;
import java.time.LocalDate;
import java.util.List;

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
