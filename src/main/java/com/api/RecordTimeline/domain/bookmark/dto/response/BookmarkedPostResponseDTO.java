package com.api.RecordTimeline.domain.bookmark.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@AllArgsConstructor
public class BookmarkedPostResponseDTO {
    private Long subTimelineId;
    private String title;
    private String content;
    private LocalDate startDate;
    private LocalDate endDate;
    private int bookmarkCount;
    private int likeCount;
    private Long mainTimelineId; // 메인 타임라인 ID
    private Long authorId; // 사용자(작성자) ID
    private String authorName; // 게시글 작성자 이름
    private String authorInterest; // 게시글 작성자 관심직종
}
