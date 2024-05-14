package com.api.RecordTimeline.global.s3;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.api.RecordTimeline.global.exception.ApiException;
import com.api.RecordTimeline.global.exception.ErrorType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import static com.api.RecordTimeline.global.exception.ErrorType.*;

@Slf4j
@RequiredArgsConstructor
@Component
@Service
public class S3FileUploader {

    private final AmazonS3 amazonS3;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    @Value("${bucket.dirname}")
    private String dirName;

    public List<String> uploadMultipartFiles(final List<MultipartFile> multipartFiles) {
        validInput(multipartFiles);
        return multipartFiles.stream()
                .map(this::uploadSingleFile)
                .toList();
    }

    public String uploadMultipartFile(final MultipartFile multipartFile) {
        return uploadSingleFile(multipartFile);
    }

    private void validInput(final List<MultipartFile> multipartFiles) {
        validFileSize(multipartFiles);
        validFileNumber(multipartFiles.size());
    }

    private void validFileSize(final List<MultipartFile> multipartFiles) {
        long maxFileSize = 10485760; // 10MB로 설정
        multipartFiles.forEach(multipartFile -> {
            if (multipartFile.getSize() > maxFileSize) {
                throw new ApiException(ErrorType.EXCEEDING_FILE_SIZE);
            }
        });
    }

    private void validFileNumber(final int size) {
        // ToDo 임시 값, 몇개로 정할지 프론트와 협의
        if (size > 10) {
            throw new ApiException(EXCEEDING_FILE_COUNT);
        }
    }

    private String uploadSingleFile(final MultipartFile multipartFile) {
        try {
            File uploadFile = convert(multipartFile);
            String timestampedFilename = appendTimestampToFilename(uploadFile.getName());
            return upload(uploadFile, dirName, timestampedFilename);
        } catch (IOException e) {
            throw new ApiException(S3_CONNECT);
        }
    }

    private String appendTimestampToFilename(String filename) {
        String timestamp = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
        int dotIndex = filename.lastIndexOf('.');
        if (dotIndex > 0) {
            String nameWithoutExtension = filename.substring(0, dotIndex);
            String extension = filename.substring(dotIndex);
            return nameWithoutExtension + "_" + timestamp + extension;
        } else {
            return filename + "_" + timestamp;
        }
    }

    private File convert(final MultipartFile file) throws IOException {
        String originalFilename = file.getOriginalFilename();
        originalFilename = validFileName(originalFilename);

        File convertFile = new File(originalFilename);
        validGenerateLocalFile(convertFile);

        try (FileOutputStream fos = new FileOutputStream(convertFile)) {
            fos.write(file.getBytes());
        }
        return convertFile;
    }

    private String validFileName(final String originalFilename) {
        if (!StringUtils.hasText(originalFilename) || originalFilename.startsWith(".")) {
            return UUID.randomUUID() + getFileExtension(originalFilename);
        }
        return originalFilename;
    }

    private String getFileExtension(String filename) {
        int dotIndex = filename.lastIndexOf('.');
        if (dotIndex > 0 && dotIndex < filename.length() - 1) {
            return filename.substring(dotIndex);
        }
        return "";
    }

    private void validGenerateLocalFile(final File convertFile) throws IOException {
        if (!convertFile.createNewFile()) {
            throw new ApiException(S3_CONVERT);
        }
    }

    private String upload(final File uploadFile, final String dirName, final String timestampedFilename) {
        String fileName = dirName + "/" + timestampedFilename;
        String uploadImageUrl = putS3(uploadFile, fileName);

        removeNewFile(uploadFile);  // 로컬 File 삭제
        return uploadImageUrl;
    }

    private String putS3(final File uploadFile, final String fileName) {
        amazonS3.putObject(new PutObjectRequest(bucket, fileName, uploadFile));
        return amazonS3.getUrl(bucket, fileName).toString();
    }

    private void removeNewFile(final File targetFile) {
        if (targetFile.delete()) {
            log.info("파일이 삭제되었습니다.");
        } else {
            log.info("파일이 삭제되지 못했습니다.");
        }
    }

    public void deleteFileFromS3(String fileUrl) {
        try {
            URI uri = new URI(fileUrl);
            String key = uri.getPath().substring(1);
            amazonS3.deleteObject(new DeleteObjectRequest(bucket, key));
        } catch (URISyntaxException e) {
            log.error("Invalid URL format: {}", fileUrl, e);
            throw new ApiException(ErrorType.INVALID_FILE_PATH);
        }
    }
}
