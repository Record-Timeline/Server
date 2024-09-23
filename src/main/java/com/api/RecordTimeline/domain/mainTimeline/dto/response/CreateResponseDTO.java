package com.api.RecordTimeline.domain.mainTimeline.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CreateResponseDTO {
    private String code;
    private String message;
    private Long timelineId;  // 타임라인 ID를 포함하는 필드 추가
    private boolean isDone; // 진행 상태 필드 추가
    private boolean isPrivate;

    // 성공 시 호출되는 메서드
    public static CreateResponseDTO success(Long timelineId, boolean isDone, boolean isPrivate) {
        return new CreateResponseDTO("SU", "Success", timelineId, isDone, isPrivate);
    }

    // 실패 시 호출되는 메서드
    public static CreateResponseDTO failure(String errorMessage) {
        return new CreateResponseDTO("UF", errorMessage, null, false, false);
    }

}
