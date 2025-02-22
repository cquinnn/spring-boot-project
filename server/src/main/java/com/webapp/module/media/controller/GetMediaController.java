package com.webapp.module.media.controller;

import com.webapp.module.media.dto.GetMediaListByPostIdRequest;
import com.webapp.module.media.dto.GetMediaOutput;
import com.webapp.module.media.service.GetMediaListByPostIdService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/media")
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class GetMediaController {
    GetMediaListByPostIdService getMediaService;

    //test show img
    @PostMapping("/get")
    public ResponseEntity<byte[]> get(
            @RequestParam("id") Long id) {
        GetMediaListByPostIdRequest request = new GetMediaListByPostIdRequest(id);
        try {
            List<GetMediaOutput> media = getMediaService.getMediaList(request);
            return ResponseEntity.ok()
                    .contentType(MediaType.valueOf(media.get(0).getContentType()))
                    .body(media.get(0).getContent());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
