package com.api.RecordTimeline.domain.appLogin.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class AppLoginRequestDto {

    @NotBlank
    @Email
    private String email;

    @NotBlank
    private String password;
}
