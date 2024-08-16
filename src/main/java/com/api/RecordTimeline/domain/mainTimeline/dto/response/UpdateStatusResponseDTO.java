package com.api.RecordTimeline.domain.mainTimeline.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UpdateStatusResponseDTO {
    private String code;
    private String message;

    public static UpdateStatusResponseDTO success(String message) {
        return new UpdateStatusResponseDTO("SU", message);
    }

    public static UpdateStatusResponseDTO failure(String message) {
        return new UpdateStatusResponseDTO("UF", message);
    }
}
