package com.api.RecordTimeline.domain.commentlike.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CommentLikeResponseDTO {
    private String code;
    private String message;
    private int likeCount; // 현재 댓글의 좋아요 수

    // 좋아요 성공 응답
    public static CommentLikeResponseDTO success(String message, int likeCount) {
        return new CommentLikeResponseDTO("SU", message, likeCount);
    }

    // 좋아요 실패 응답
    public static CommentLikeResponseDTO failure(String message) {
        return new CommentLikeResponseDTO("UF", message, 0);
    }
}
