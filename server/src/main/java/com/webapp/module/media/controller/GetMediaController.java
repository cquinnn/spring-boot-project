//package com.webapp.module.media.controller;
//
//import com.webapp.common.dto.response.ApiResponse;
//import com.webapp.module.media.dto.GetMediaRequest;
//import com.webapp.module.media.dto.MediaResponse;
//import com.webapp.module.media.service.GetMediaListService;
//import lombok.AccessLevel;
//import lombok.RequiredArgsConstructor;
//import lombok.experimental.FieldDefaults;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RestController;
//
//@RestController
//@RequestMapping("/media")
//@RequiredArgsConstructor
//@Slf4j
//@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
//public class GetMediaController {
//    GetMediaListService getMediaService;
//
//    @PostMapping("/get")
//    public ApiResponse<MediaResponse> delete(
//            @RequestParam("id") String id) {
//        GetMediaRequest request = new GetMediaRequest(id);
//        try {
//            MediaResponse media = getMediaService.getFile(request);
//            return ApiResponse.<MediaResponse>builder()
//                    .result(media)
//                    .build();
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }
//    }
//}
