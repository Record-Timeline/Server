package com.api.RecordTimeline.domain.signup.email.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@AllArgsConstructor
@RequiredArgsConstructor
public class EmailCertificationRequestDto {

    @Schema(description = "사용자 이메일", example = "사용자 이메일 예시")
    @Email
    @NotBlank
    private String email;

    @Schema(description = "이메일 인증 목적 구분", example = "SIGNUP, PASSWORD_RESET")
    @NotBlank
    private String context;
}
