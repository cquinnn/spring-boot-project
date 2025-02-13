package com.webapp.module.media.controller;

import com.webapp.common.dto.response.ApiResponse;
import com.webapp.common.exception.AppException;
import com.webapp.common.exception.ErrorCode;
import com.webapp.module.media.dto.MediaResponse;
import com.webapp.module.media.dto.UploadMediaListRequest;
import com.webapp.module.media.service.UploadMediaListService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/media")
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UploadMediaListController {
    UploadMediaListService uploadMediaListService;

    @PostMapping("/upload")
    public ApiResponse<List<MediaResponse>> uploadMedia(
            @RequestParam("mediaList") List<MultipartFile> mediaList,
            @RequestParam("postId") Long postId
    ) throws IOException {
        UploadMediaListRequest request = new UploadMediaListRequest(mediaList);
        List<MediaResponse> savedMedia;
        try {
            savedMedia = uploadMediaListService.save(request, postId);
        } catch (IllegalArgumentException e) {
            throw new AppException(ErrorCode.INVALID_MEDIA_TYPE);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        if (savedMedia != null) {
            return ApiResponse.<List<MediaResponse>>builder()
                    .message("Saved the media successfully.")
                    .result(savedMedia)
                    .build();
        } else {
            return ApiResponse.<List<MediaResponse>>builder()
                    .message(ErrorCode.FILE_STORAGE_FAILURE.getMessage())
                    .build();
        }
    }

}
