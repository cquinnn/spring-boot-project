package com.webapp.module.blogpost.controller;

import com.webapp.common.dto.response.ApiResponse;
import com.webapp.common.exception.ErrorCode;
import com.webapp.module.blogpost.dto.GetPostRequest;
import com.webapp.module.blogpost.dto.GetPostResponse;
import com.webapp.module.blogpost.service.GetPostService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/post")
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class GetPostController {
    GetPostService getPostService;

    @PostMapping("/get")
    public ApiResponse<GetPostResponse> get(Long id) {
        GetPostRequest request = new GetPostRequest(id);
        GetPostResponse postResponse = getPostService.get(request);
        if (postResponse != null) {
            return ApiResponse.<GetPostResponse>builder()
                    .result(postResponse)
                    .build();
        } else {
            return ApiResponse.<GetPostResponse>builder()
                    .message(ErrorCode.FILE_STORAGE_FAILURE.getMessage())
                    .build();
        }
    }
}
