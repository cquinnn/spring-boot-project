package com.webapp.module.blogpost.service;

import com.webapp.model.Media;
import com.webapp.model.Post;
import com.webapp.module.blogpost.dto.PostResponse;
import com.webapp.module.blogpost.dto.UploadPostRequest;
import com.webapp.module.blogpost.mapper.PostMapper;
import com.webapp.module.blogpost.repository.PostRepository;
import com.webapp.module.media.dto.MediaResponse;
import com.webapp.module.media.dto.UploadMediaListRequest;
import com.webapp.module.media.mapper.MediaMapper;
import com.webapp.module.media.service.UploadMediaListService;
import com.webapp.module.user.dto.response.UserResponse;
import com.webapp.module.user.entity.User;
import com.webapp.module.user.mapper.UserMapper;
import com.webapp.module.user.service.UserService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class UploadPostService {
    PostMapper postMapper;
    PostRepository postRepository;
    UserService userService;
    UserMapper userMapper;
    UploadMediaListService uploadMediaListService;
    MediaMapper mediaMapper;

    @Transactional
    public PostResponse save(UploadPostRequest request) throws IOException {
        Post post = postMapper.toPost(request);
        post.setCreatedAt(LocalDateTime.now());

        UserResponse userResponse = userService.getMyInfo();
        User user = userMapper.toUser(userResponse);
        post.setAuthor(user);
        List<MultipartFile> mediaFiles = request.getMediaList();

        Post savedPost = postRepository.save(post);
        if (mediaFiles != null && !mediaFiles.isEmpty()) {
            UploadMediaListRequest uploadMediaListRequest = new UploadMediaListRequest(mediaFiles);

            List<MediaResponse> mediaResponses = uploadMediaListService.save(uploadMediaListRequest, savedPost.getId());
            List<Media> mediaList = mediaMapper.toMediaList(mediaResponses);
            savedPost.setMediaList(mediaList);
        }
        return postMapper.toPostResponse(savedPost);
    }
}
