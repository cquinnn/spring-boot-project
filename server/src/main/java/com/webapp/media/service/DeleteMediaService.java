package com.webapp.media.service;

import com.webapp.common.dto.response.ApiResponse;
import com.webapp.common.exception.AppException;
import com.webapp.common.exception.ErrorCode;
import com.webapp.media.dto.DeleteMediaRequest;
import com.webapp.media.entity.Media;
import com.webapp.media.repository.MediaRepository;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;


@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Slf4j
public class DeleteMediaService {
    final MediaRepository mediaRepository;

    @Value("${media.upload.directory}")
    private String mediaDirectory;

    @Transactional
    public synchronized ApiResponse<Void> delete(DeleteMediaRequest request) throws IOException {
        Media media = mediaRepository.findById(request.getId()).orElseThrow(() -> new AppException(ErrorCode.MEDIA_NOT_EXISTED));

        Path directoryPath = Paths.get(mediaDirectory);
        if (!Files.exists(directoryPath)) {
            throw new NoSuchFileException("Directory not found: " + directoryPath);
        }
        Path path = Path.of(media.getFilePath());
        if (!Files.exists(path)) {
            throw new AppException(ErrorCode.MEDIA_NOT_EXISTED);
        }

        Files.delete(path);
        mediaRepository.deleteById(request.getId());

        return ApiResponse.<Void>builder().build();
    }
}
