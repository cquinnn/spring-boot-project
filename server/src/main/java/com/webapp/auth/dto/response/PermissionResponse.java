package com.webapp.auth.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Value
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PermissionResponse {
  String name;
  String description;
}
