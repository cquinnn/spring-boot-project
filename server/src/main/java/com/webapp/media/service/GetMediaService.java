package com.webapp.media.service;

import com.webapp.common.exception.AppException;
import com.webapp.common.exception.ErrorCode;
import com.webapp.media.dto.GetMediaRequest;
import com.webapp.media.dto.MediaResponse;
import com.webapp.media.entity.Media;
import com.webapp.media.mapper.MediaMapper;
import com.webapp.media.repository.MediaRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Slf4j
public class GetMediaService {
    final MediaMapper mediaMapper;
    final MediaRepository mediaRepository;

    @Value("${media.upload.directory}")
    private String mediaUploadDirectory;

    public MediaResponse get(GetMediaRequest request) {
        Media media = mediaRepository.findById(request.getId()).orElseThrow(() -> new AppException(ErrorCode.MEDIA_NOT_EXISTED));

        return mediaMapper.toMediaResponse(media);
    }
}
