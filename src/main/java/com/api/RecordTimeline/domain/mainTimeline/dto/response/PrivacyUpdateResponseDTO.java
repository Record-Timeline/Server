package com.api.RecordTimeline.domain.mainTimeline.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PrivacyUpdateResponseDTO {
    private String code;
    private String message;

    public static PrivacyUpdateResponseDTO success(String message) {
        return new PrivacyUpdateResponseDTO("SU", message);
    }

    public static PrivacyUpdateResponseDTO failure(String message) {
        return new PrivacyUpdateResponseDTO("UF", message);
    }
}
