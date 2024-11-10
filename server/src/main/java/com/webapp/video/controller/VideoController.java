package com.webapp.video.controller;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.val;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.File;
import java.net.URL;
import java.nio.file.Paths;
import java.nio.file.Path;
import java.nio.file.Files;

import java.util.Optional;
import java.util.UUID;

import org.apache.tomcat.util.http.fileupload.ByteArrayOutputStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.MediaTypeFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import com.webapp.common.dto.response.ApiResponse;
import com.webapp.user.dto.response.UserResponse;
import com.webapp.video.entity.Video;
import com.webapp.video.service.VideoService;

@RestController
@RequestMapping("/video")
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class VideoController {
    VideoService videoService;
    @PostMapping("/upload")
    public ApiResponse<Video> saveVideo(@RequestParam("file") MultipartFile file, @RequestParam("title") String title, @RequestParam("description") String description) throws IOException {
        Video video = new Video();
        video.setTitle(title);
        video.setDescription(description);
        video.setId(UUID.randomUUID().toString());

        Video savedVideo = videoService.save(video, file);

        if (savedVideo != null) {
            return ApiResponse.<Video>builder()
                .message("Saved the video.")
                .result(savedVideo)
                .build();
        } else {
            return ApiResponse.<Video>builder()
                .message("Could not save the video.")
                .build();
        }
    }
    

    // @GetMapping()
    // public void initialize() throws IOException{
    //     byte[] data = Files.readAllBytes(Paths.get("D:\\practice\\practice\\server\\src\\main\\resources\\static\\video.mp4"));
    //     System.out.println(data);
    //     videoService.saveVideo("video", );
    // }
    // Return a file
    // @GetMapping("{title}")
    // public ApiResponse<Mono<Resource>> getVideoByTitle(@PathVariable("title") String title) throws IOException {
    //     return ApiResponse.<Mono<Resource>>builder().result(videoService.getVideo(title)).build();
    // }
    
    @GetMapping(value = "{title}", produces = "video/mp4")
    public Mono<Resource> getVideo(@PathVariable String title) throws IOException {
        return videoService.getVideoAsResource(title);
    }

}
