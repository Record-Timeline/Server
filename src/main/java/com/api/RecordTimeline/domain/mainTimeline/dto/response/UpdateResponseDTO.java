package com.api.RecordTimeline.domain.mainTimeline.dto.response;

import com.api.RecordTimeline.domain.common.ResponseCode;
import com.api.RecordTimeline.domain.common.ResponseMessage;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UpdateResponseDTO {
    private final String code;
    private final String message;

    public static UpdateResponseDTO success() {
        return new UpdateResponseDTO(ResponseCode.SUCCESS, ResponseMessage.SUCCESS);
    }

    public static UpdateResponseDTO failure() {
        return new UpdateResponseDTO(ResponseCode.UPDATE_FAILED, ResponseMessage.UPDATE_FAILED);
    }
}
