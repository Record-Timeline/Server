package com.api.RecordTimeline.domain.member.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class MemberInfoResponseDto {
    private final Long memberId;
    private final String nickname;
    private final String interest;
    private final String profileImageUrl;
    private final String introduction;
}
