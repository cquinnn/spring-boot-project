package com.webapp.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
public class Media {
    @Id
    String id;

    String filename;
    String filePath;
    String contentType;
    LocalDateTime timestamp;
    Long postId;
}
