package com.webapp.module.blogpost.dto;

import com.webapp.module.media.dto.GetMediaOutput;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class GetPostResponse {
    Long id;
    String title;
    String content;
    String author;
    LocalDateTime createdAt;
    LocalDateTime lastUpdated;
    List<GetMediaOutput> mediaList;
}
