package com.api.RecordTimeline.domain.signup.duplicateCheck.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@AllArgsConstructor
@RequiredArgsConstructor
public class NicknameCheckResquestDto {

    @Schema(description = "사용자 닉네임", example = "사용자 닉네임 예시")
    @NotBlank
    private String nickname;
}
