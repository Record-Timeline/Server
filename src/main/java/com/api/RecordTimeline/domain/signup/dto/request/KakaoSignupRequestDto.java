package com.api.RecordTimeline.domain.signup.dto.request;

import com.api.RecordTimeline.domain.member.domain.Interest;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@AllArgsConstructor
@RequiredArgsConstructor
public class KakaoSignupRequestDto {

    @NotBlank
    private String nickname;

    @NotBlank
    private Interest interest;
}