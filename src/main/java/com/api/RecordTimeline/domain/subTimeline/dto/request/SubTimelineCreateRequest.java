package com.api.RecordTimeline.domain.subTimeline.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.List;

import lombok.Data;

import java.time.LocalDate;

@Data
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SubTimelineCreateRequest {
    private Long mainTimelineId;
    private String title;
    private String content;
    private LocalDate startDate;
    private LocalDate endDate;
}
