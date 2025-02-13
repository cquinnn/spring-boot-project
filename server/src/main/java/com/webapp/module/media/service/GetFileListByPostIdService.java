package com.webapp.module.media.service;

import com.webapp.model.Media;
import com.webapp.module.media.dto.GetFileListByPostIdRequest;
import com.webapp.module.media.mapper.MediaMapper;
import com.webapp.module.media.repository.MediaRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Slf4j
public class GetFileListByPostIdService {
    final MediaMapper mediaMapper;
    final MediaRepository mediaRepository;

    @Value("${media.upload.directory}")
    String mediaUploadDirectory;

    public List<File> getFileList(GetFileListByPostIdRequest request) {
        List<Media> mediaList = mediaRepository.findAllByPostId(request.getPostId());
        return mediaList.stream().map(this::getFileFromMedia)
                .toList();
    }

    private File getFileFromMedia(Media media) {
        String filePath = media.getFilePath();

        File file = new File(filePath);

        if (file.exists() && file.isFile()) {
            return file;
        } else {
            throw new RuntimeException("File does not exist at path: " + filePath);
        }
    }

    private List<File> convertMediaListToFiles(List<Media> mediaList) throws IOException {
        List<File> files = new ArrayList<>();
        for (Media media : mediaList) {
            files.add(getFileFromMedia(media));  // Gọi hàm chuyển đổi
        }
        return files;
    }
}
