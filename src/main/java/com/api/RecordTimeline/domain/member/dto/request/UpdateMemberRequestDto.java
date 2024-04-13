package com.api.RecordTimeline.domain.member.dto.request;

import com.api.RecordTimeline.domain.member.domain.Interest;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class UpdateMemberRequestDto {


    @NotBlank
    private String newNickname;
    private Interest newInterest;
}
