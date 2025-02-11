package com.webapp.module.media.service;

import com.webapp.common.exception.AppException;
import com.webapp.common.exception.ErrorCode;
import com.webapp.module.media.dto.GetMediaRequest;
import com.webapp.module.media.dto.MediaResponse;
import com.webapp.model.Media;
import com.webapp.module.media.mapper.MediaMapper;
import com.webapp.module.media.repository.MediaRepository;
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
    String mediaUploadDirectory;

    public MediaResponse get(GetMediaRequest request) {
        Media media = mediaRepository.findById(request.getId()).orElseThrow(() -> new AppException(ErrorCode.MEDIA_NOT_EXISTED));

        return mediaMapper.toMediaResponse(media);
    }
}
