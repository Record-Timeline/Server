package com.api.RecordTimeline.domain.signup.email.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter //추후에 변경
@NoArgsConstructor
public class CheckCertificationRequestDto {
    @NotBlank
    private String memberId;
    
    @Email
    @NotBlank
    private String email;
    
    @NotBlank
    private String certificationNumber;
}
