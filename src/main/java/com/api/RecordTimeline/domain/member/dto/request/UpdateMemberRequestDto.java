package com.api.RecordTimeline.domain.member.dto.request;

import com.api.RecordTimeline.domain.member.domain.Interest;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class UpdateMemberRequestDto {

    private String newNickname;
    private Interest newInterest;
}
