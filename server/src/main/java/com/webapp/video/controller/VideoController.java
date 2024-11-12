package com.webapp.video.controller;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

import java.io.IOException;
import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import com.webapp.common.dto.response.ApiResponse;
import com.webapp.video.dto.VideoResponse;
import com.webapp.video.dto.VideoUploadRequest;
import com.webapp.video.service.VideoService;

@RestController
@RequestMapping("/video")
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class VideoController {
    VideoService videoService;
    @PostMapping("/upload")
    public ApiResponse<VideoResponse> saveVideo(@RequestParam("file") MultipartFile file, @RequestParam("title") String title, @RequestParam("description") String description) throws IOException {
        VideoUploadRequest request = new VideoUploadRequest(title, description);
        VideoResponse savedVideo = videoService.save(request, file);

        if (savedVideo != null) {
            return ApiResponse.<VideoResponse>builder()
                .message("Saved the video.")
                .result(savedVideo)
                .build();
        } else {
            return ApiResponse.<VideoResponse>builder()
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
