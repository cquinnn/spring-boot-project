package com.webapp.module.user.dto.request;

import java.time.LocalDate;
import java.util.List;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Value
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserUpdateRequest {
  String password;
  String firstName;
  String lastName;
  LocalDate dob;
}
