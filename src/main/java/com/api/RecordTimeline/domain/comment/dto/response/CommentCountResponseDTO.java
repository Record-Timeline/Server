package com.api.RecordTimeline.domain.comment.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CommentCountResponseDTO {
    private String status;  // 상태 메시지 (선택 사항)
    private int totalCount; // 총 댓글 + 대댓글 수
}
