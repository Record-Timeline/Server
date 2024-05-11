package com.api.RecordTimeline.domain.subTimeline.service;

import com.api.RecordTimeline.global.s3.S3FileUploader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class S3Service {
    private final S3FileUploader s3FileUploader;

    @Autowired
    public S3Service(S3FileUploader s3FileUploader) {
        this.s3FileUploader = s3FileUploader;
    }

    // 파일 업로드, 다운로드, 삭제 등의 로직을 포함 가능
    public String uploadFile(MultipartFile file) {
        return s3FileUploader.uploadMultipartFile(file);
    }

    public void deleteFile(String fileUrl) {
        s3FileUploader.deleteFileFromS3(fileUrl);
    }
}
