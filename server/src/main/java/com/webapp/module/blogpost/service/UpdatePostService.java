package com.webapp.module.blogpost.service;

import com.webapp.common.exception.AppException;
import com.webapp.common.exception.ErrorCode;
import com.webapp.model.Post;
import com.webapp.module.blogpost.dto.PostResponse;
import com.webapp.module.blogpost.dto.UpdatePostRequest;
import com.webapp.module.blogpost.mapper.PostMapper;
import com.webapp.module.blogpost.repository.PostRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import static java.time.LocalDateTime.now;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class UpdatePostService {
    PostMapper postMapper;
    PostRepository postRepository;

    public synchronized PostResponse update(UpdatePostRequest request) {
        Post post = postRepository.findById(request.getId()).orElseThrow(() -> new AppException(ErrorCode.POST_NOT_EXISTED));
        postMapper.updatePost(post, request);
        post.setLastUpdated(now());
        return postMapper.toPostResponse(postRepository.save(post));
    }
}
