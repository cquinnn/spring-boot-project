package com.webapp.blogpost.entity;

import com.webapp.user.entity.User;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
public class Post {
    @Id
    String id;

    String title;

    @Size(max = 1000, message = "The post must not exceed 500 characters.")
    String content;
    LocalDateTime timestamp;

    @ManyToOne
    User author;
}