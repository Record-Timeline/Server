package com.api.RecordTimeline.domain.replylike.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReplyLikeRequestDTO {
    private Long replyId; // 좋아요를 추가 또는 해제할 대댓글 ID
}
