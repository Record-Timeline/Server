package com.api.RecordTimeline.domain.subTimeline.service;

import com.api.RecordTimeline.domain.mainTimeline.domain.MainTimeline;
import com.api.RecordTimeline.domain.mainTimeline.repository.MainTimelineRepository;
import com.api.RecordTimeline.domain.subTimeline.domain.SubTimeline;
import com.api.RecordTimeline.domain.subTimeline.dto.SubTimelineCreateRequest;
import com.api.RecordTimeline.domain.subTimeline.repository.SubTimelineRepository;
import com.api.RecordTimeline.global.s3.S3FileUploader;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SubTimelineService {
    private final SubTimelineRepository subTimelineRepository;
    private final MainTimelineRepository mainTimelineRepository;
    private final S3FileUploader s3FileUploader;

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

    public List<SubTimeline> getSubTimelinesByMainTimelineId(Long mainTimelineId) {
        return subTimelineRepository.findByMainTimelineId(mainTimelineId);
    }
}
