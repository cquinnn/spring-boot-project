package com.webapp.module.blogpost.dto;
import com.webapp.model.Comment;
import com.webapp.module.user.entity.User;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PostResponse {
    String title;
    String content;
    User author;
    List<Comment> comments;
}
