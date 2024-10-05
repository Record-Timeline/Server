package com.api.RecordTimeline.domain.comment.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CommentUpdateRequestDTO {
    private String content;  // 수정할 댓글 내용

    public CommentUpdateRequestDTO(String content) {
        this.content = content;
    }
}
