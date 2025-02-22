package com.webapp.module.blogpost.service;

import com.webapp.common.exception.AppException;
import com.webapp.common.exception.ErrorCode;
import com.webapp.model.Post;
import com.webapp.module.blogpost.dto.GetPostRequest;
import com.webapp.module.blogpost.dto.GetPostResponse;
import com.webapp.module.blogpost.mapper.PostMapper;
import com.webapp.module.blogpost.repository.PostRepository;
import com.webapp.module.media.dto.GetMediaListByPostIdRequest;
import com.webapp.module.media.dto.GetMediaOutput;
import com.webapp.module.media.mapper.MediaMapper;
import com.webapp.module.media.service.GetMediaListByPostIdService;
import com.webapp.module.media.service.UploadMediaListService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class GetPostService {
    PostMapper postMapper;
    PostRepository postRepository;
    UploadMediaListService uploadMediaListService;
    MediaMapper mediaMapper;
    GetMediaListByPostIdService getMediaListByPostIdService;

    public GetPostResponse get(GetPostRequest request) {
        Post post = postRepository.findById(request.getId())
                .orElseThrow(() -> new AppException(ErrorCode.POST_NOT_EXISTED));
        GetMediaListByPostIdRequest getMediaListByPostIdRequest = new GetMediaListByPostIdRequest(post.getId());
        // Get MultipartFile instead of File
        try {
            List<GetMediaOutput> files = getMediaListByPostIdService.getMediaList(getMediaListByPostIdRequest);

            GetPostResponse postResponse = postMapper.toGetPostResponse(post);
            postResponse.setMediaList(files);
            return postResponse;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
