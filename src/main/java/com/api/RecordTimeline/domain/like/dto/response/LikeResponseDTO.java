package com.api.RecordTimeline.domain.like.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class LikeResponseDTO {
    private String code;
    private String message;
    private int likeCount;

    public static LikeResponseDTO success(String message, int likeCount) {
        return new LikeResponseDTO("SU", message, likeCount);
    }

    public static LikeResponseDTO failure(String message) {
        return new LikeResponseDTO("UF", message, 0);
    }
}

