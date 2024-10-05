package com.api.RecordTimeline.domain.reply.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ReplyUpdateRequestDTO {
    private String content;  // 수정할 대댓글 내용

    public ReplyUpdateRequestDTO(String content) {
        this.content = content;
    }
}
