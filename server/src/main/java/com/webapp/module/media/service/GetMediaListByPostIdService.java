package com.webapp.module.media.service;

import com.webapp.model.Media;
import com.webapp.module.media.dto.GetMediaListByPostIdRequest;
import com.webapp.module.media.dto.GetMediaOutput;
import com.webapp.module.media.repository.MediaRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Slf4j
public class GetMediaListByPostIdService {
    final MediaRepository mediaRepository;

    public List<GetMediaOutput> getMediaList(GetMediaListByPostIdRequest request) throws IOException {
        List<Media> mediaList = mediaRepository.findAllByPostId(request.getPostId());
        List<GetMediaOutput> mediaOutputs = new ArrayList<>();
        for (Media media : mediaList) {
            String filename = media.getFilename();
            byte[] fileContent = Files.readAllBytes(Paths.get(media.getFilePath()));
            GetMediaOutput mediaOutput = new GetMediaOutput(media.getId(), filename, media.getContentType(), fileContent, media.getTimestamp());
            mediaOutputs.add(mediaOutput);
        }
        return mediaOutputs;
    }
//
//    private File getFileFromMedia(Media media) {
//        String filePath = media.getFilePath();
//
//        File file = new File(filePath);
//
//        if (file.exists() && file.isFile()) {
//            return file;
//        } else {
//            throw new RuntimeException("File does not exist at path: " + filePath);
//        }
//    }
//
//    private List<File> convertMediaListToFiles(List<Media> mediaList) throws IOException {
//        List<File> files = new ArrayList<>();
//        for (Media media : mediaList) {
//            files.add(getFileFromMedia(media));  // Gọi hàm chuyển đổi
//        }
//        return files;
//    }
}
