package com.api.RecordTimeline.domain.subTimeline.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class SubUpdateStatusResponseDTO {

    private String code;
    private String message;

    public static SubUpdateStatusResponseDTO success(boolean isDone) {
        String message = isDone ? "서브타임라인이 완료 상태로 업데이트 되었습니다." : "서브타임라인이 진행중 상태로 업데이트 되었습니다.";
        return new SubUpdateStatusResponseDTO("SU", message);
    }

    public static SubUpdateStatusResponseDTO failure(String message) {
        return new SubUpdateStatusResponseDTO("UF", message);
    }
}