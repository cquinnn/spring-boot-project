package com.webapp.media.mapper;

import com.webapp.media.dto.MediaResponse;
import com.webapp.media.dto.UploadMediaRequest;
import com.webapp.media.entity.Media;
import com.webapp.user.entity.User;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;

@Mapper(componentModel = "spring")
public interface MediaMapper {
    @Mapping(target = "contentType", ignore = true)
    @Mapping(target = "filePath", ignore = true)
    @Mapping(target = "id", ignore = true)
    Media toMedia(UploadMediaRequest request);
    MediaResponse toMediaResponse(Media Media);
}
