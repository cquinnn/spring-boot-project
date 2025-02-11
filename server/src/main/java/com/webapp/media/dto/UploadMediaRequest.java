package com.webapp.media.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;


@Data
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UploadMediaRequest {
    String title;
    String description;
}