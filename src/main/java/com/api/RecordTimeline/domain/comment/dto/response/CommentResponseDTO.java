package com.api.RecordTimeline.domain.comment.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CommentResponseDTO {
    private Long id;
    private String content;
    private String createdDate;
    private String nickname;  // 작성자 닉네임
    private int replyCount;    // 대댓글 수
    private int likeCount; // 댓글 좋아요 수
}

