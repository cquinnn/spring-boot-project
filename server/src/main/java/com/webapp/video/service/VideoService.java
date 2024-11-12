package com.webapp.video.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.beans.factory.annotation.Value;

import com.webapp.common.exception.AppException;
import com.webapp.common.exception.ErrorCode;
import com.webapp.video.dto.VideoResponse;
import com.webapp.video.dto.VideoUploadRequest;
import com.webapp.video.entity.Video;
import com.webapp.video.mapper.VideoMapper;
import com.webapp.video.repository.VideoRepository;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Slf4j
public class VideoService {
    
    final VideoMapper videoMapper;
    final VideoRepository videoRepository;

    @Value("${video.upload.directory}")
    private String staticUploadDirectory;

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

    // public ApiResponse<Video> getVideoMetadata(String title) {
    // return ApiResponse<Video>.
    //     Video video = videoRepository.findByTitle(title)
    //                             .orElseThrow(() -> new AppException(ErrorCode.VIDEO_NOT_EXISTED));
        
    // });
    // }
    
    public VideoResponse save(VideoUploadRequest request, MultipartFile file) throws IllegalStateException, IOException {
        String filename = file.getOriginalFilename();
        String contentType = file.getContentType();
        String videoId = UUID.randomUUID().toString();
        String uniqueFileName = videoId + "_" + filename;
        
        // Create the directory if it doesn't exist
        Path directoryPath = Paths.get(staticUploadDirectory);
        if (!Files.exists(directoryPath)) {
            Files.createDirectories(directoryPath);
        }
        
        // Define the path to save the video
        Path filePath = directoryPath.resolve(uniqueFileName).normalize().toAbsolutePath();
        Video video = new Video();
        video = videoMapper.toVideo(request);
        video.setId(videoId);
        video.setFilePath(filePath.toString());
        video.setContentType(contentType);
        try{
            Video savedVideo = videoRepository.save(video);
            file.transferTo(filePath.toFile()); // Save the file
            return videoMapper.toVideoResponse(savedVideo); 
        } catch(DataIntegrityViolationException e){
            throw new AppException(ErrorCode.VIDEO_TITLE_DUPLICATED);
        } catch(IOException e){
            Files.delete(filePath); 
            throw new RuntimeException("File storage failed. Rolling back video .", e);
        }
    }
}
