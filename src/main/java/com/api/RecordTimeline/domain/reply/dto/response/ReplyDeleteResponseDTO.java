package com.api.RecordTimeline.domain.reply.dto.response;

import com.api.RecordTimeline.domain.common.ResponseCode;
import com.api.RecordTimeline.domain.common.ResponseMessage;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ReplyDeleteResponseDTO {
    private String code;
    private String message;

    public static ReplyDeleteResponseDTO success() {
        return new ReplyDeleteResponseDTO(ResponseCode.SUCCESS, ResponseMessage.SUCCESS);
    }

    public static ReplyDeleteResponseDTO failure() {
        return new ReplyDeleteResponseDTO(ResponseCode.UPDATE_FAILED, ResponseMessage.UPDATE_FAILED);
    }
}
