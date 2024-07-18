package com.api.RecordTimeline.domain.bookmark.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class BookmarkResponseDTO {
    private String code;
    private String message;
    private Long bookmarkId;  // 북마크 ID를 포함하는 필드 추가

    // 성공 시 호출되는 메서드
    public static BookmarkResponseDTO success(Long bookmarkId) {
        return new BookmarkResponseDTO("SU", "Success", bookmarkId);
    }

    // 실패 시 호출되는 메서드
    public static BookmarkResponseDTO failure() {
        return new BookmarkResponseDTO("UF", "Bookmark operation failed", null);
    }
}
