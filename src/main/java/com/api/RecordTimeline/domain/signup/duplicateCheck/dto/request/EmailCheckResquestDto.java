package com.api.RecordTimeline.domain.signup.duplicateCheck.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@AllArgsConstructor
@RequiredArgsConstructor
public class EmailCheckResquestDto {

    @Schema(description = "사용자 이메일", example = "사용자 이메일 예시")
    @NotBlank
    @Email
    private String email;
}
