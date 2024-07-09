package com.api.RecordTimeline.domain.subTimeline.service;

import com.api.RecordTimeline.domain.mainTimeline.domain.MainTimeline;
import com.api.RecordTimeline.domain.mainTimeline.repository.MainTimelineRepository;
import com.api.RecordTimeline.domain.subTimeline.domain.SubTimeline;
import com.api.RecordTimeline.domain.subTimeline.dto.request.SubTimelineCreateRequest;
import com.api.RecordTimeline.domain.subTimeline.repository.SubTimelineRepository;
import com.api.RecordTimeline.global.exception.ApiException;
import com.api.RecordTimeline.global.exception.ErrorType;
import com.api.RecordTimeline.global.s3.S3FileUploader;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SubTimelineService {

    private final SubTimelineRepository subTimelineRepository;
    private final MainTimelineRepository mainTimelineRepository;
    private final S3FileUploader s3FileUploader;
    private final ImageUploadService imageUploadService;

    private static final Pattern IMAGE_URL_PATTERN = Pattern.compile("<img[^>]+src=\"([^\"]+)\"");

    public SubTimeline createSubTimeline(SubTimelineCreateRequest request) {
        MainTimeline mainTimeline = mainTimelineRepository.findById(request.getMainTimelineId())
                .orElseThrow(() -> new IllegalArgumentException("해당 메인타임라인을 찾을 수 없습니다. : " + request.getMainTimelineId()));

        // Base64 이미지를 URL로 변환
        String contentWithUrls = replaceBase64WithUrls(request.getContent());

        SubTimeline subTimeline = SubTimeline.builder()
                .mainTimeline(mainTimeline)
                .title(request.getTitle())
                .content(contentWithUrls)
                .startDate(request.getStartDate())
                .endDate(request.getEndDate())
                .build();
        return subTimelineRepository.save(subTimeline);
    }

    public SubTimeline updateSubTimeline(Long subTimelineId, SubTimelineCreateRequest request) {
        SubTimeline existingSubTimeline = subTimelineRepository.findById(subTimelineId)
                .orElseThrow(() -> new IllegalArgumentException("해당 서브타임라인을 찾을 수 없습니다. : " + subTimelineId));

        // 기존 이미지 URL 추출
        List<String> existingImageUrls = extractImageUrls(existingSubTimeline.getContent());

        // Base64 이미지를 URL로 변환
        String contentWithUrls = replaceBase64WithUrls(request.getContent());

        // 새 이미지 URL 추출
        List<String> newImageUrls = extractImageUrls(contentWithUrls);

        // 삭제할 URL 찾기
        List<String> urlsToDelete = existingImageUrls.stream()
                .filter(url -> !newImageUrls.contains(url))
                .collect(Collectors.toList());

        // S3에서 제거된 이미지 삭제
        urlsToDelete.forEach(s3FileUploader::deleteFileFromS3);

        // subTimeline 업데이트
        SubTimeline updatedSubTimeline = existingSubTimeline.toBuilder()
                .title(request.getTitle())
                .content(contentWithUrls)
                .startDate(request.getStartDate())
                .endDate(request.getEndDate())
                .build();

        return subTimelineRepository.save(updatedSubTimeline);
    }

    private List<String> extractImageUrls(String content) {
        Matcher matcher = IMAGE_URL_PATTERN.matcher(content);
        List<String> imageUrls = new ArrayList<>();
        while (matcher.find()) {
            imageUrls.add(matcher.group(1));
        }
        return imageUrls;
    }

    private String replaceBase64WithUrls(String content) {
        if (content == null || content.isEmpty()) {
            return content;
        }

        Matcher matcher = IMAGE_URL_PATTERN.matcher(content);
        StringBuffer result = new StringBuffer();

        while (matcher.find()) {
            String base64Image = matcher.group(1);

            // Base64 형식인지 확인
            if (base64Image.startsWith("data:image")) {
                String imageUrl = imageUploadService.uploadBase64Image(base64Image);
                matcher.appendReplacement(result, "<img src=\"" + imageUrl + "\"");
            } else {
                matcher.appendReplacement(result, matcher.group(0)); // 기존 URL 유지
            }
        }
        matcher.appendTail(result);
        return result.toString();
    }

    public void deleteSubTimeline(Long subTimelineId) {
        SubTimeline subTimeline = subTimelineRepository.findById(subTimelineId)
                .orElseThrow(() -> new IllegalArgumentException("SubTimeline not found"));
        checkOwnership(subTimeline.getMainTimeline().getMember().getEmail());

        subTimelineRepository.delete(subTimeline);
    }

    public List<SubTimeline> getSubTimelinesByMainTimelineId(Long mainTimelineId) {
        return subTimelineRepository.findByMainTimelineId(mainTimelineId);
    }

    // 서브 타임라인 시작 날짜 기준으로 정렬
    public List<SubTimeline> getSubTimelinesByMainTimelineIdOrderByStartDate(Long mainTimelineId) {
        return subTimelineRepository.findByMainTimelineIdOrderByStartDate(mainTimelineId);
    }

    // 메인타임라인 제목을 가져오는 메서드 추가
    public String getMainTimelineTitle(Long mainTimelineId) {
        return mainTimelineRepository.findById(mainTimelineId)
                .map(MainTimeline::getTitle)
                .orElseThrow(() -> new IllegalArgumentException("해당 메인타임라인을 찾을 수 없습니다. : " + mainTimelineId));
    }

    private void checkOwnership(String ownerEmail) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userEmail = authentication.getName();
        if (!userEmail.equals(ownerEmail)) {
            throw new ApiException(ErrorType._DO_NOT_HAVE_PERMISSION);
        }
    }
}