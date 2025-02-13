package com.webapp.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
public class Like {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long Id;

//    @ManyToOne
//    @JoinColumn(name = "post_id")
//    private Post post; // Like này thuộc về một post
//
//    @ManyToOne
//    @JoinColumn(name = "user_id")
//    private User user;
}
