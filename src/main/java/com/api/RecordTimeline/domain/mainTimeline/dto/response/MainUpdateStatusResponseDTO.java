package com.api.RecordTimeline.domain.mainTimeline.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MainUpdateStatusResponseDTO {

    private String code;
    private String message;

    public static MainUpdateStatusResponseDTO success(boolean isDone) {
        String message = isDone ? "메인타임라인이 완료 상태로 업데이트 되었습니다." : "메인타임라인이 진행중 상태로 업데이트 되었습니다.";
        return new MainUpdateStatusResponseDTO("SU", message);
    }

    public static MainUpdateStatusResponseDTO failure(String message) {
        return new MainUpdateStatusResponseDTO("UF", message);
    }
}
