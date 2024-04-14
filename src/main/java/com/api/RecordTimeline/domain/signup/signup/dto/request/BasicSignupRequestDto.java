package com.api.RecordTimeline.domain.signup.signup.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@AllArgsConstructor
@Builder
@Setter
public class BasicSignupRequestDto extends KakaoSignupRequestDto {

    @Schema(description = "사용자 이메일", example = "사용자 이메일 예시")
    @Email
    @NotBlank
    private String email;

    @Schema(description = "사용자 비밀번호", example = "abcde12345 (영문,숫자 포함 8글자 이상)")
    @NotBlank
    @Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*[0-9])[a-zA-Z0-9\\p{Punct}]{8,}$")
    private String password;

    @Schema(description = "사용자 이름", example = "사용자 이름 예시")
    @NotBlank
    private String name;


    @Schema(description = "이메일 인증 번호", example = "이메일 인증 번호 예시")
    @NotBlank
    private String certificationNumber;

}