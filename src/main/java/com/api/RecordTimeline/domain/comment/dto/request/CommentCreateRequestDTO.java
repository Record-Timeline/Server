package com.api.RecordTimeline.domain.comment.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CommentCreateRequestDTO {
    private Long subTimelineId;
    private Long memberId;
    private String content;
}

