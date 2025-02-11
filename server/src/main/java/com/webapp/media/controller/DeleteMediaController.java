package com.webapp.media.controller;

import com.webapp.common.dto.response.ApiResponse;
import com.webapp.media.dto.DeleteMediaRequest;
import com.webapp.media.dto.MediaResponse;
import com.webapp.media.service.DeleteMediaService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.nio.file.NoSuchFileException;

@RestController
@RequestMapping("/media")
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class DeleteMediaController {
    DeleteMediaService deleteMediaService;

    @PostMapping("/delete")
    public ApiResponse<MediaResponse> delete(
            @RequestParam("id") String id) {
        DeleteMediaRequest request = new DeleteMediaRequest(id);
        try {
            deleteMediaService.delete(request);
            return ApiResponse.<MediaResponse>builder()
                    .message("Delete file successfully.")
                    .build();
        } catch (NoSuchFileException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
