package com.api.RecordTimeline.domain.mainTimeline.dto.response;

import com.api.RecordTimeline.domain.common.ResponseMessage;
import com.api.RecordTimeline.domain.common.ResponseCode;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CreateResponseDTO {
    private String code;
    private String message;
    private Long timelineId;

    public static CreateResponseDTO success(Long timelineId) {
        return new CreateResponseDTO(ResponseCode.SUCCESS, ResponseMessage.SUCCESS, timelineId);
    }

    public static CreateResponseDTO failure() {
        return new CreateResponseDTO(ResponseCode.UPDATE_FAILED, ResponseMessage.UPDATE_FAILED, null);
    }
}
