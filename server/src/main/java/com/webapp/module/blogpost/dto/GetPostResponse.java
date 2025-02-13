package com.webapp.module.blogpost.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.io.File;
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
    List<File> fileList;
}
