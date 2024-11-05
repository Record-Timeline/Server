package com.api.RecordTimeline.domain.replylike.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ReplyLikeResponseDTO {
    private String code;
    private String message;
    private int likeCount; // 현재 대댓글의 좋아요 수

    // 좋아요 성공 응답
    public static ReplyLikeResponseDTO success(String message, int likeCount) {
        return new ReplyLikeResponseDTO("SU", message, likeCount);
    }

    // 좋아요 실패 응답
    public static ReplyLikeResponseDTO failure(String message) {
        return new ReplyLikeResponseDTO("UF", message, 0);
    }
}
