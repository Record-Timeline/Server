package com.api.RecordTimeline.domain.subTimeline.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class SubPrivacyUpdateResponseDTO {
    private String code;
    private String message;

    public static SubPrivacyUpdateResponseDTO success(String message) {
        return new SubPrivacyUpdateResponseDTO("SU", message);
    }

    public static SubPrivacyUpdateResponseDTO failure(String message) {
        return new SubPrivacyUpdateResponseDTO("UF", message);
    }
}
