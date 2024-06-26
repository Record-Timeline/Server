package com.api.RecordTimeline.domain.search.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SearchSubTimelineDto {
    private Long id;
    private Long mainTimelineId;
    private String title;
    private String content;
    private LocalDate startDate;
    private LocalDate endDate;
    private Long authorId;
    private String authorNickname;
    private String authorInterest;
}