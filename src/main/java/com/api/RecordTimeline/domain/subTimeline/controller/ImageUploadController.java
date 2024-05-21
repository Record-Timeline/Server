package com.api.RecordTimeline.domain.subTimeline.controller;

import com.api.RecordTimeline.domain.subTimeline.service.ImageUploadService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/images")
public class ImageUploadController {

    private final ImageUploadService imageUploadService;

    @PostMapping("/upload-base64")
    public ResponseEntity<String> uploadBase64Image(@RequestBody Base64ImageRequest request) {
        String imageUrl = imageUploadService.uploadBase64Image(request.getBase64Image());
        return ResponseEntity.ok(imageUrl);
    }

    @Data
    public static class Base64ImageRequest {
        private String base64Image;
    }
}