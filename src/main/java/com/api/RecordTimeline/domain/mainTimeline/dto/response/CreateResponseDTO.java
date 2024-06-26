package com.api.RecordTimeline.domain.mainTimeline.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CreateResponseDTO {
    private String code;
    private String message;
    private Long timelineId;  // 타임라인 ID를 포함하는 필드 추가

    // 성공 시 호출되는 메서드
    public static CreateResponseDTO success(Long timelineId) {
        return new CreateResponseDTO("SU", "Success", timelineId);
    }

    // 실패 시 호출되는 메서드
    public static CreateResponseDTO failure() {
        return new CreateResponseDTO("UF", "Update Failed", null);
    }
}
