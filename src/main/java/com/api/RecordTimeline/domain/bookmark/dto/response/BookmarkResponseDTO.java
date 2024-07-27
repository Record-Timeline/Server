package com.api.RecordTimeline.domain.bookmark.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class BookmarkResponseDTO {
    private String code;
    private String message;
    private int bookmarkCount;

    // 성공 시 호출되는 메서드
    public static BookmarkResponseDTO success(String message, int bookmarkCount) {
//        return new BookmarkResponseDTO("SU", "Success", bookmarkId);
        return new BookmarkResponseDTO("SU", message, bookmarkCount);
    }

    // 실패 시 호출되는 메서드
    public static BookmarkResponseDTO failure() {
        return new BookmarkResponseDTO("UF", "Bookmark operation failed", 0);
    }
}
