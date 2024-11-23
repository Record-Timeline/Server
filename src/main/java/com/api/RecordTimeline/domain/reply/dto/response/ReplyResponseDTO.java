package com.api.RecordTimeline.domain.reply.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReplyResponseDTO {
    private Long id;
    private String content;
    private String createdDate;
    private String nickname;  // 작성자 닉네임
    private Long subTimelineId;
    private Long mainTimelineId;
    private Long memberId;

}
