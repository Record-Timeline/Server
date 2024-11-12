package com.api.RecordTimeline.domain.commentlike.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CommentLikeRequestDTO {
    private Long commentId; // 좋아요를 추가 또는 해제할 댓글 ID
}
