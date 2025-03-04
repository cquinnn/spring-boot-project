package com.webapp.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long Id;

    String title;

    @Size(max = 1000, message = "The post must not exceed 500 characters.")
    String content;
    LocalDateTime createdAt;
    LocalDateTime lastUpdated;

    @ManyToOne(fetch = FetchType.LAZY)
    User author;

    @OneToMany(mappedBy = "postId")
    List<Media> mediaList = new ArrayList<>();
//
//    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
//    List<Comment> comments; // Nhiều comment cho một post
//
//    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
//    List<Like> likes;
}