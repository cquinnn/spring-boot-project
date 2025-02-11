package com.webapp.module.media.dto;
import lombok.Builder;
import lombok.Value;
import lombok.experimental.FieldDefaults;

import lombok.AccessLevel;

@Value
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class MediaResponse {
    String id;
    String title;
    String description;
    String contentType;
}
