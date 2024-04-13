package com.api.RecordTimeline.domain.signup.signup.dto.request;

import com.api.RecordTimeline.domain.member.domain.Interest;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@AllArgsConstructor
@RequiredArgsConstructor
public class KakaoSignupRequestDto {

    @Schema(description = "사용자 닉네임", example = "사용자 닉네임 예시")
    @NotBlank
    private String nickname;

    @Schema(description = "관심 분야", example = "IT_Data or Design or MedicalCare or Education .... ")
    private Interest interest;
}