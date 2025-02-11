package com.webapp.media.service;

import com.webapp.common.exception.AppException;
import com.webapp.common.exception.ErrorCode;
import com.webapp.media.dto.MediaResponse;
import com.webapp.media.dto.UploadMediaRequest;
import com.webapp.media.entity.Media;
import com.webapp.media.mapper.MediaMapper;
import com.webapp.media.repository.MediaRepository;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Slf4j
public class UploadMediaService {

    final MediaMapper mediaMapper;
    final MediaRepository mediaRepository;

    @Value("${media.upload.directory}")
    private String mediaUploadDirectory;

    @Transactional
    public synchronized MediaResponse save(UploadMediaRequest request, MultipartFile file) throws IllegalArgumentException, IllegalStateException, IOException {
        if (!validateImageorVideo(file)) {
            throw new IllegalArgumentException("Type of file is invalid.");
        }
        String filename = file.getOriginalFilename();
        String contentType = file.getContentType();
        String id = UUID.randomUUID().toString();
        String uniqueFileName = id + "_" + filename;

        Path directoryPath = Paths.get(mediaUploadDirectory);
        if (!Files.exists(directoryPath)) {
            Files.createDirectories(directoryPath);
        }

        Path filePath = directoryPath.resolve(uniqueFileName).normalize().toAbsolutePath();
        Media media = mediaMapper.toMedia(request);
        media.setId(id);
        media.setFilePath(filePath.toString());
        media.setContentType(contentType);
        try {
            Media savedMedia = mediaRepository.save(media);
            file.transferTo(filePath.toFile()); // Save the file
            return mediaMapper.toMediaResponse(savedMedia);
        } catch (DataIntegrityViolationException e) {
            throw new AppException(ErrorCode.MEDIA_TITLE_DUPLICATED);
        } catch (IOException e) {
            Files.delete(filePath);
            throw new AppException(ErrorCode.MEDIA_STORAGE_FAILURE);
        }
    }

    private boolean validateImageorVideo(MultipartFile file) throws IOException {
        String contentType = file.getContentType();
        return contentType != null && (contentType.startsWith("image/") || contentType.startsWith("video/"));
    }
}


