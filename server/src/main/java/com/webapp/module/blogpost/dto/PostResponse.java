package com.webapp.module.blogpost.dto;

import com.webapp.module.media.dto.MediaResponse;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PostResponse {
    Long id;
    String title;
    String content;
    String author;
    LocalDateTime createdAt;
    LocalDateTime lastUpdated;
    List<MediaResponse> mediaList;
}
