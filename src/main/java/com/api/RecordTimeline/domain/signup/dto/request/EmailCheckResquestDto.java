package com.api.RecordTimeline.domain.signup.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@AllArgsConstructor
@RequiredArgsConstructor
public class EmailCheckResquestDto {

    @NotNull
    @Email
    private String email;
}
