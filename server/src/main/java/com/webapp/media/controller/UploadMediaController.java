package com.webapp.media.controller;

import com.webapp.common.dto.response.ApiResponse;
import com.webapp.common.exception.AppException;
import com.webapp.common.exception.ErrorCode;
import com.webapp.media.dto.MediaResponse;
import com.webapp.media.dto.UploadMediaRequest;
import com.webapp.media.service.UploadMediaService;
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

@RestController
@RequestMapping("/media")
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UploadMediaController {
    UploadMediaService uploadMediaService;

    @PostMapping("/upload")
    public ApiResponse<MediaResponse> uploadMedia(
            @RequestParam("file") MultipartFile file,
            @RequestParam("title") String title,
            @RequestParam("description") String description
    ) throws IOException {
        UploadMediaRequest request = new UploadMediaRequest(title, description);
        MediaResponse savedMedia;
        try {
            savedMedia = uploadMediaService.save(request, file);
        } catch (IllegalArgumentException e) {
            throw new AppException(ErrorCode.INVALID_TYPE);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        if (savedMedia != null) {
            return ApiResponse.<MediaResponse>builder()
                    .message("Saved the photo.")
                    .result(savedMedia)
                    .build();
        } else {
            return ApiResponse.<MediaResponse>builder()
                    .message("Could not save the photo.")
                    .build();
        }
    }
}
