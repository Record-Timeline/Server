package com.api.RecordTimeline.domain.subTimeline.service;

import com.api.RecordTimeline.domain.mainTimeline.domain.MainTimeline;
import com.api.RecordTimeline.domain.mainTimeline.repository.MainTimelineRepository;
import com.api.RecordTimeline.domain.subTimeline.domain.SubTimeline;
import com.api.RecordTimeline.domain.subTimeline.dto.SubTimelineCreateRequest;
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
    private S3Service s3Service;  // S3Service 클래스 인스턴스 주입

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
        SubTimeline existingSubTimeline = subTimelineRepository.findById(subTimelineId)
                .orElseThrow(() -> new IllegalArgumentException("SubTimeline not found"));

        List<String> imageUrls = s3FileUploader.uploadMultipartFiles(request.getImages());

        SubTimeline updatedSubTimeline = SubTimeline.builder()
                .mainTimeline(existingSubTimeline.getMainTimeline()) // 기존 메인 타임라인 참조
                .title(request.getTitle())
                .content(request.getContent())
                .startDate(request.getStartDate())
                .endDate(request.getEndDate())
                .imageUrls(imageUrls) // 이미지 URL 업데이트
                .build();

        // 기존 엔티티 삭제
        subTimelineRepository.delete(existingSubTimeline);
        // 새로운 엔티티 저장
        return subTimelineRepository.save(updatedSubTimeline);
    }


    public void deleteSubTimeline(Long subTimelineId) {
        SubTimeline subTimeline = subTimelineRepository.findById(subTimelineId)
                .orElseThrow(() -> new IllegalArgumentException("SubTimeline not found"));
        subTimelineRepository.delete(subTimeline);
        // 연관된 이미지 리소스도 S3에서 삭제
        subTimeline.getImageUrls().forEach(s3Service::deleteFile);
    }



    public List<SubTimeline> getSubTimelinesByMainTimelineId(Long mainTimelineId) {
        return subTimelineRepository.findByMainTimelineId(mainTimelineId);
    }
}
