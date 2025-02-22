package com.webapp.module.media.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class GetMediaOutput {
    String id;
    String filename;
    String contentType;
    byte[] content;
    LocalDateTime timestamp;
}
