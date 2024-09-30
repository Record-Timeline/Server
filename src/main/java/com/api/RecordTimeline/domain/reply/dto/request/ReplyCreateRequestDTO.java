package com.api.RecordTimeline.domain.reply.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReplyCreateRequestDTO {
    private Long commentId; // 대댓글이 속한 댓글의 ID
    private Long memberId; // 작성자 ID
    private String content; // 대댓글 내용
}
