package com.webapp.module.media.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class MediaResponse {
    String id;
    String contentType;
    String filename;
    LocalDateTime timestamp;
}
