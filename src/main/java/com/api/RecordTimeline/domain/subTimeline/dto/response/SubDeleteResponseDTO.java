package com.api.RecordTimeline.domain.subTimeline.dto.response;

import com.api.RecordTimeline.domain.common.ResponseCode;
import com.api.RecordTimeline.domain.common.ResponseMessage;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class SubDeleteResponseDTO {
    private String code;
    private String message;

    public static SubDeleteResponseDTO success() {
        return new SubDeleteResponseDTO(ResponseCode.SUCCESS, ResponseMessage.SUCCESS);
    }

    public static SubDeleteResponseDTO failure() {
        return new SubDeleteResponseDTO(ResponseCode.UPDATE_FAILED, ResponseMessage.UPDATE_FAILED);
    }
}
