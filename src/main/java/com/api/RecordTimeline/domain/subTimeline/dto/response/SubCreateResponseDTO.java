package com.api.RecordTimeline.domain.subTimeline.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class SubCreateResponseDTO {
    private String code;
    private String message;
    private Long subTimelineId;
    private String errorDetails;

    public static SubCreateResponseDTO success(Long subTimelineId) {
        return new SubCreateResponseDTO("SU", "Success", subTimelineId, null);
    }

    public static SubCreateResponseDTO failure(String message, String errorDetails) {
        return new SubCreateResponseDTO("UF", message, null, errorDetails);
    }
}
