package com.webapp.video.service;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;
import org.springframework.beans.factory.annotation.Value;

import com.webapp.common.exception.AppException;
import com.webapp.common.exception.ErrorCode;
import com.webapp.video.entity.Video;
import com.webapp.video.repository.VideoRepository;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE)
@Service
public class VideoService {
    
    final VideoRepository videoRepository;

    @Value("${video.upload.directory}")
    String uploadDirectory;
    
    public Mono<Resource> getVideoAsResource(String title) throws IOException{
    
        Video video = videoRepository.findByTitle(title)
                                .orElseThrow(() -> new AppException(ErrorCode.VIDEO_NOT_EXISTED));
        byte[] videoData;
        videoData = Files.readAllBytes(Paths.get(video.getFilePath()));
        Resource resource = new ByteArrayResource(videoData);
        if (resource.exists()) {
            return Mono.just(resource);
        } else {
            return Mono.error(new AppException(ErrorCode.VIDEO_NOT_EXISTED));
        }
    }

    // public Mono<Video> getVideoMetadata(String title) {
    // return Mono.fromCallable(() -> {
    //     Video video = videoRepository.findByTitle(title)
    //                             .orElseThrow(() -> new AppException(ErrorCode.VIDEO_NOT_EXISTED));
        
    // });
    // }
    
    public Video save(Video video, MultipartFile file) throws IllegalStateException, IOException {
        String filename = file.getOriginalFilename();
        String contentType = file.getContentType();

        String uniqueFileName = video.getId() + "_" + filename;
        
        // Create the directory if it doesn't exist
        Path directoryPath = Paths.get(uploadDirectory);
        if (!Files.exists(directoryPath)) {
            Files.createDirectories(directoryPath);
        }
        
        // Define the path to save the video
        Path filePath = directoryPath.resolve(uniqueFileName).normalize().toAbsolutePath();
        
        video.setContentType(contentType);
        video.setFilePath(filePath.toString());
        try{
            Video savedVideo = videoRepository.save(video);
            file.transferTo(filePath.toFile()); // Save the file
            return savedVideo; 
        } catch(DataIntegrityViolationException e){
            throw new AppException(ErrorCode.VIDEO_TITLE_DUPLICATED);
        } catch(IOException e){
            Files.delete(filePath); 
            throw new RuntimeException("File storage failed. Rolling back video .", e);
        }
    }
}
