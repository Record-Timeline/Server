package com.api.RecordTimeline.domain.comment.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CommentCreateRequestDTO {
    private Long subTimelineId;
    private String content;
}

