package com.webapp.module.blogpost.service;

import com.webapp.module.blogpost.dto.PostResponse;
import com.webapp.module.blogpost.dto.UploadPostRequest;
import com.webapp.model.Post;
import com.webapp.module.blogpost.mapper.PostMapper;
import com.webapp.module.blogpost.repository.PostRepository;
import com.webapp.module.user.dto.response.UserResponse;
import com.webapp.module.user.entity.User;
import com.webapp.module.user.mapper.UserMapper;
import com.webapp.module.user.service.UserService;
import jakarta.transaction.Transactional;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class UploadPostService {
    PostMapper postMapper;
    PostRepository postRepository;
    UserService userService;
    UserMapper userMapper;

    @Transactional
    public synchronized PostResponse save(UploadPostRequest request) {
        String id = UUID.randomUUID().toString();
        Post post = postMapper.toPost(request);
        post.setTimestamp(LocalDateTime.now());
        var context = SecurityContextHolder.getContext();
        String name = context.getAuthentication().getName();
        UserResponse userResponse= userService.getMyInfo();
        User user = userMapper.toUser(userResponse);
        post.setAuthor(user);

        try {
            Post savedPost = postRepository.save(post);
            return postMapper.toPostResponse(savedPost);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
