package com.webapp.module.blogpost.controller;

import com.webapp.common.dto.response.ApiResponse;
import com.webapp.common.exception.ErrorCode;
import com.webapp.module.blogpost.dto.PostResponse;
import com.webapp.module.blogpost.dto.UploadPostRequest;
import com.webapp.module.blogpost.service.UploadPostService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/post")
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UploadPostController {
    UploadPostService uploadPostService;

    @PostMapping("/upload")
    public ApiResponse<PostResponse> uploadPost(
            @RequestParam("title") String title,
            @RequestParam("content") String content,
            @RequestPart(value = "mediaList", required = false) List<MultipartFile> mediaList
    ) {
        try {
            UploadPostRequest request = new UploadPostRequest(title, content, mediaList);
            PostResponse savedPost = uploadPostService.save(request);
            if (savedPost != null) {
                return ApiResponse.<PostResponse>builder()
                        .message("Saved the post sucessfully.")
                        .result(savedPost)
                        .build();
            } else {
                return ApiResponse.<PostResponse>builder()
                        .message(ErrorCode.FILE_STORAGE_FAILURE.getMessage())
                        .build();
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
