package com.api.RecordTimeline.domain.reply.dto.response;

import com.api.RecordTimeline.domain.common.ResponseCode;
import com.api.RecordTimeline.domain.common.ResponseMessage;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ReplyUpdateResponseDTO {
    private String code;
    private String message;

    public static ReplyUpdateResponseDTO success() {
        return new ReplyUpdateResponseDTO(ResponseCode.SUCCESS, ResponseMessage.SUCCESS);
    }

    public static ReplyUpdateResponseDTO failure() {
        return new ReplyUpdateResponseDTO(ResponseCode.UPDATE_FAILED, ResponseMessage.UPDATE_FAILED);
    }
}
