package com.webapp.module.media.service;

import com.webapp.common.exception.AppException;
import com.webapp.common.exception.ErrorCode;
import com.webapp.model.Media;
import com.webapp.module.blogpost.repository.PostRepository;
import com.webapp.module.media.dto.MediaResponse;
import com.webapp.module.media.dto.UploadMediaListRequest;
import com.webapp.module.media.mapper.MediaMapper;
import com.webapp.module.media.repository.MediaRepository;
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
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static java.time.LocalDateTime.now;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Slf4j
public class UploadMediaListService {

    final MediaMapper mediaMapper;
    final MediaRepository mediaRepository;
    final PostRepository postRepository;

    @Value("${media.upload.directory}")
    String mediaUploadDirectory;

    @Transactional
    public List<MediaResponse> save(UploadMediaListRequest request, Long postId) throws IllegalArgumentException, IllegalStateException, IOException {
        List<MultipartFile> mediaList = request.getMediaList();
        if (mediaList.isEmpty()) {
            throw new IllegalArgumentException("No media files provided.");
        }
        boolean isValidated = mediaList.stream().allMatch(this::validateImageorVideo);
        if (!isValidated) {
            throw new IllegalArgumentException(ErrorCode.INVALID_MEDIA_TYPE.getMessage());
        }
        boolean exists = postRepository.existsById(postId);
        if (!exists) {
            throw new AppException(ErrorCode.POST_NOT_EXISTED);
        }

        List<Media> mediaListToSave = new ArrayList<>();
        for (MultipartFile file : mediaList) {
            String originalFilename = file.getOriginalFilename();
            String extension = "";
            if (originalFilename != null && originalFilename.contains(".")) {
                extension = originalFilename.substring(originalFilename.lastIndexOf("."));
            }

            String id = UUID.randomUUID().toString();
            String uniqueFileName = id + extension;
            String contentType = file.getContentType();

            Path directoryPath = Paths.get(mediaUploadDirectory);
            if (!Files.exists(directoryPath)) {
                Files.createDirectories(directoryPath);
            }

            Path filePath = directoryPath.resolve(uniqueFileName).normalize().toAbsolutePath();
            file.transferTo(filePath.toFile());

            Media media = mediaMapper.toMedia(request);
            media.setId(id);
            media.setFilename(originalFilename);
            media.setFilePath(filePath.toString());
            media.setContentType(contentType);
            media.setTimestamp(now());
            media.setPostId(postId);
            mediaListToSave.add(media);
        }

        try {
            List<Media> saved = mediaRepository.saveAll(mediaListToSave);

            return mediaMapper.toMediaListResponse(saved);
        } catch (DataIntegrityViolationException e) {
            throw new AppException(ErrorCode.MEDIA_TITLE_DUPLICATED);
        }
    }

    private boolean validateImageorVideo(MultipartFile file) {
        String contentType = file.getContentType();
        return contentType != null && (contentType.startsWith("image/") || contentType.startsWith("video/"));
    }

}


