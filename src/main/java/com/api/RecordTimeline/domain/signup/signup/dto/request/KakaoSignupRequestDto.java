package com.api.RecordTimeline.domain.signup.signup.dto.request;

import com.api.RecordTimeline.domain.member.domain.Interest;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@AllArgsConstructor
@RequiredArgsConstructor
public class KakaoSignupRequestDto {

    @NotBlank
    private String nickname;

    private Interest interest;
}