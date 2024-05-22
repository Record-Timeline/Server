package com.api.RecordTimeline.domain.subTimeline.service;

import com.api.RecordTimeline.domain.subTimeline.domain.SubTimeline;
import com.api.RecordTimeline.domain.subTimeline.repository.SubTimelineRepository;
import com.api.RecordTimeline.global.s3.S3FileUploader;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.net.URI;
import java.net.URISyntaxException;

@Service
@RequiredArgsConstructor
public class S3Service {
    private final S3FileUploader s3FileUploader;
    private final SubTimelineRepository subTimelineRepository;

    // 파일 업로드, 다운로드, 삭제 등의 로직을 포함 가능
    public String uploadFile(MultipartFile file) {
        return s3FileUploader.uploadMultipartFile(file);
    }

    public void deleteFile(String fileUrl) {
        s3FileUploader.deleteFileFromS3(fileUrl);
    }

    public void deleteSubTimelineImages(Long subTimelineId) {
        SubTimeline subTimeline = subTimelineRepository.findById(subTimelineId)
                .orElseThrow(() -> new IllegalStateException("SubTimeline not found"));

        subTimeline.getImageUrls().forEach(this::deleteFileFromS3);
    }

    private void deleteFileFromS3(String fileUrl) {
        try {
            URI uri = new URI(fileUrl);
            String key = uri.getPath().substring(1);
            s3FileUploader.deleteFileFromS3(key);
        } catch (URISyntaxException e) {
            throw new RuntimeException("Invalid URL format", e);
        }
    }
}
