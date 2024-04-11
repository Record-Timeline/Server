package com.api.RecordTimeline.domain.signup.email.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@AllArgsConstructor
public class CheckCertificationRequestDto {

    @Schema(description = "사용자 이메일", example = "사용자 이메일 예시")
    @Email
    @NotBlank
    private String email;

    @Schema(description = "이메일 인증 번호", example = "이메일 인증 번호 예시")
    @NotBlank
    private String certificationNumber;
}
