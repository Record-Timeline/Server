package com.api.RecordTimeline.domain.signup.email.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@AllArgsConstructor
@RequiredArgsConstructor
public class EmailCertificationRequestDto {

    @NotBlank
    private String memberId;

    @Email
    @NotBlank
    private String email;
}
