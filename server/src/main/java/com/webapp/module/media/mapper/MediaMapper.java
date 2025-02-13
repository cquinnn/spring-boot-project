package com.webapp.module.media.mapper;

import com.webapp.model.Media;
import com.webapp.module.media.dto.MediaResponse;
import com.webapp.module.media.dto.UploadMediaListRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface MediaMapper {
    @Mapping(target = "filePath", ignore = true)
    Media toMedia(UploadMediaListRequest request);

    MediaResponse toMediaResponse(Media media);

    List<MediaResponse> toMediaListResponse(List<Media> mediaList);

    List<Media> toMediaList(List<MediaResponse> mediaListResponse);
}
