package com.api.RecordTimeline.domain.comment.dto.response;

import com.api.RecordTimeline.domain.common.ResponseCode;
import com.api.RecordTimeline.domain.common.ResponseMessage;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CommentUpdateResponseDTO {
    private String code;
    private String message;

    public static CommentUpdateResponseDTO success() {
        return new CommentUpdateResponseDTO(ResponseCode.SUCCESS, ResponseMessage.SUCCESS);
    }

    public static CommentUpdateResponseDTO failure() {
        return new CommentUpdateResponseDTO(ResponseCode.UPDATE_FAILED, ResponseMessage.UPDATE_FAILED);
    }
}