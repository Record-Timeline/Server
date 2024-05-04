package com.api.RecordTimeline.domain.mainTimeline.dto.response;

import com.api.RecordTimeline.domain.common.ResponseCode;
import com.api.RecordTimeline.domain.common.ResponseMessage;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class DeleteResponseDTO {
    private String code;
    private String message;

    public static DeleteResponseDTO success() {
        return new DeleteResponseDTO(ResponseCode.SUCCESS, ResponseMessage.SUCCESS);
    }

    public static DeleteResponseDTO failure() {
        return new DeleteResponseDTO(ResponseCode.UPDATE_FAILED, ResponseMessage.UPDATE_FAILED);
    }
}
