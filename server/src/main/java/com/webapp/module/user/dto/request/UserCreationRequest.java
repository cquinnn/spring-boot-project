package com.webapp.module.user.dto.request;

import com.webapp.module.user.validator.DobConstraint;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Value
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserCreationRequest {
  @Size(min = 3, message = "USERNAME_INVALID")
  String username;

  @Size(min = 8, message = "PASSWORD_INVALID")
  String password;

  String firstName;
  String lastName;

  @DobConstraint(min = 12, message = "INVALID_DOB")
  LocalDate dob;
}
