package com.api.RecordTimeline.domain.subTimeline.service;

import com.api.RecordTimeline.domain.mainTimeline.domain.MainTimeline;
import com.api.RecordTimeline.domain.mainTimeline.repository.MainTimelineRepository;
import com.api.RecordTimeline.domain.subTimeline.domain.SubTimeline;
import com.api.RecordTimeline.domain.subTimeline.dto.request.SubTimelineCreateRequest;
import com.api.RecordTimeline.domain.subTimeline.repository.SubTimelineRepository;
import com.api.RecordTimeline.global.s3.S3FileUploader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SubTimelineService {
    private final SubTimelineRepository subTimelineRepository;
    private final MainTimelineRepository mainTimelineRepository;
    private final S3FileUploader s3FileUploader;

    @Autowired
    public SubTimelineService(SubTimelineRepository subTimelineRepository, MainTimelineRepository mainTimelineRepository, S3FileUploader s3FileUploader) {
        this.subTimelineRepository = subTimelineRepository;
        this.mainTimelineRepository = mainTimelineRepository;
        this.s3FileUploader = s3FileUploader;
    }

    public SubTimeline createSubTimeline(SubTimelineCreateRequest request) {
        MainTimeline mainTimeline = mainTimelineRepository.findById(request.getMainTimelineId())
                .orElseThrow(() -> new IllegalArgumentException("Main timeline not found with ID: " + request.getMainTimelineId()));
        List<String> imageUrls = s3FileUploader.uploadMultipartFiles(request.getImages());
        SubTimeline subTimeline = SubTimeline.builder()
                .mainTimeline(mainTimeline)
                .title(request.getTitle())
                .content(request.getContent())
                .startDate(request.getStartDate())
                .endDate(request.getEndDate())
                .imageUrls(imageUrls)
                .build();
        return subTimelineRepository.save(subTimeline);
    }

    public SubTimeline updateSubTimeline(Long subTimelineId, SubTimelineCreateRequest request) {
        // 기존 서브 타임라인 조회
        SubTimeline existingSubTimeline = subTimelineRepository.findById(subTimelineId)
                .orElseThrow(() -> new IllegalArgumentException("SubTimeline not found"));

        // 이미지 파일들을 S3에 업로드하고 URL 리스트를 받아옴
        List<String> imageUrls = s3FileUploader.uploadMultipartFiles(request.getImages());

        // 기존 엔티티 정보를 이용하여 빌더를 사용해 새로운 인스턴스 생성
        SubTimeline updatedSubTimeline = SubTimeline.builder()
                .id(existingSubTimeline.getId()) // 기존 ID 유지
                .mainTimeline(existingSubTimeline.getMainTimeline())
                .title(request.getTitle())
                .content(request.getContent())
                .startDate(request.getStartDate())
                .endDate(request.getEndDate())
                .imageUrls(imageUrls)
                .build();

        // 변경된 엔티티 저장
        return subTimelineRepository.save(updatedSubTimeline);
    }

    public void deleteSubTimeline(Long subTimelineId) {
        SubTimeline subTimeline = subTimelineRepository.findById(subTimelineId)
                .orElseThrow(() -> new IllegalArgumentException("SubTimeline not found"));
        // 연관된 이미지 리소스도 S3에서 삭제
        subTimeline.getImageUrls().forEach(s3FileUploader::deleteFileFromS3);
        subTimelineRepository.delete(subTimeline);
    }

    public List<SubTimeline> getSubTimelinesByMainTimelineId(Long mainTimelineId) {
        return subTimelineRepository.findByMainTimelineId(mainTimelineId);
    }

    // 서브 타임라인을 시작 날짜 기준으로 정렬하여 조회하는 메소드 추가
    public List<SubTimeline> getSubTimelinesByMainTimelineIdOrderByStartDate(Long mainTimelineId) {
        return subTimelineRepository.findByMainTimelineIdOrderByStartDate(mainTimelineId);
    }
}
