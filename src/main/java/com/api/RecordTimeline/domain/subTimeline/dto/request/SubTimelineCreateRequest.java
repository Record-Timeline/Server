package com.api.RecordTimeline.domain.subTimeline.dto.request;

import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.List;

public class SubTimelineCreateRequest {
    private Long mainTimelineId;
    private String title;
    private String content;
    private LocalDate startDate;
    private LocalDate endDate;
    private List<MultipartFile> images;

    // Getters and Setters

    public Long getMainTimelineId() {
        return mainTimelineId;
    }

    public void setMainTimelineId(Long mainTimelineId) {
        this.mainTimelineId = mainTimelineId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public List<MultipartFile> getImages() {
        return images;
    }

    public void setImages(List<MultipartFile> images) {
        this.images = images;
    }
}
