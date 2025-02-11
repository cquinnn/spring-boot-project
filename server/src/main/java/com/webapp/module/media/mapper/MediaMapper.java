package com.webapp.module.media.mapper;

import com.webapp.module.media.dto.MediaResponse;
import com.webapp.module.media.dto.UploadMediaRequest;
import com.webapp.model.Media;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface MediaMapper {
    @Mapping(target = "contentType", ignore = true)
    @Mapping(target = "filePath", ignore = true)
    @Mapping(target = "id", ignore = true)
    Media toMedia(UploadMediaRequest request);
    MediaResponse toMediaResponse(Media Media);
}
