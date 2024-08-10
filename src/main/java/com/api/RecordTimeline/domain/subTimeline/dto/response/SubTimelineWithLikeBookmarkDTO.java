package com.api.RecordTimeline.domain.subTimeline.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@AllArgsConstructor
public class SubTimelineWithLikeBookmarkDTO {
    private Long id;
    private String title;
    private String content;
    private LocalDate startDate;
    private LocalDate endDate;
    private int likeCount;
    private int bookmarkCount;
    private boolean liked;
    private boolean bookmarked;
}

