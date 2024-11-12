package com.webapp.video.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.webapp.video.dto.VideoResponse;
import com.webapp.video.dto.VideoUploadRequest;
import com.webapp.video.entity.Video;

@Mapper(componentModel = "spring")
public interface VideoMapper {
    @Mapping(target = "contentType", ignore = true)
    @Mapping(target = "filePath", ignore = true)
    @Mapping(target = "id", ignore = true)
    Video toVideo(VideoUploadRequest request);
    VideoResponse toVideoResponse(Video video);
}
