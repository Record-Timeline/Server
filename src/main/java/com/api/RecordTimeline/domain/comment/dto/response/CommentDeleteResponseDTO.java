package com.api.RecordTimeline.domain.comment.dto.response;

import com.api.RecordTimeline.domain.common.ResponseCode;
import com.api.RecordTimeline.domain.common.ResponseMessage;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CommentDeleteResponseDTO {
    private String code;
    private String message;

    public static CommentDeleteResponseDTO success() {
        return new CommentDeleteResponseDTO(ResponseCode.SUCCESS, ResponseMessage.SUCCESS);
    }

    public static CommentDeleteResponseDTO failure() {
        return new CommentDeleteResponseDTO(ResponseCode.UPDATE_FAILED, ResponseMessage.UPDATE_FAILED);
    }
}