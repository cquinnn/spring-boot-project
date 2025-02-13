package com.webapp.module.user.dto.request;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Value;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Value
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UpdateUserRequest {
    String password;
    String firstName;
    String lastName;
    LocalDate dob;
}
