package com.api.RecordTimeline.domain.notification.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class RelateInfoDto {
    private Long postId;
    private Long memberId;
    private Long mainTimelineId;
}

