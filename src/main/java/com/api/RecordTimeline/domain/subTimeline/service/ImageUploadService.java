package com.api.RecordTimeline.domain.subTimeline.service;

import com.api.RecordTimeline.global.s3.S3FileUploader;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ImageUploadService {

    private final S3FileUploader s3FileUploader;

    public String uploadBase64Image(String base64Image) {
        return s3FileUploader.uploadBase64Image(base64Image);
    }
}