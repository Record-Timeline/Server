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
    private String authorName; // 게시글 작성자 이름
    private String authorInterest; // 게시글 작성자 관심직종

//    public static BookmarkedPostResponseDTO from(
//            Long id, String title, String content, LocalDate startDate, LocalDate endDate,
//            int bookmarkCount, String authorName, String authorInterest) {
//        return new BookmarkedPostResponseDTO(
//                id, title, content, startDate, endDate, bookmarkCount, authorName, authorInterest);
//    }
}
