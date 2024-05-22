package com.api.RecordTimeline.domain.subTimeline.dto.response;

import com.api.RecordTimeline.domain.common.ResponseCode;
import com.api.RecordTimeline.domain.common.ResponseMessage;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class SubUpdateResponseDTO {
    private String code;
    private String message;

    public static SubUpdateResponseDTO success() {
        return new SubUpdateResponseDTO(ResponseCode.SUCCESS, ResponseMessage.SUCCESS);
    }

    public static SubUpdateResponseDTO failure() {
        return new SubUpdateResponseDTO(ResponseCode.UPDATE_FAILED, ResponseMessage.UPDATE_FAILED);
    }
}
