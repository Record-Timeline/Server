package com.api.RecordTimeline.domain.subTimeline.dto.response;

import com.api.RecordTimeline.domain.mainTimeline.dto.response.CreateResponseDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class SubCreateResponseDTO {
    private String code;
    private String message;
    private Long subTimelineId;

    public static SubCreateResponseDTO success(Long subTimelineId) {
        return new SubCreateResponseDTO("SU", "Success", subTimelineId);
    }

    public static SubCreateResponseDTO failure() {
        return new SubCreateResponseDTO("UF", "Update Failed", null);
    }
}
