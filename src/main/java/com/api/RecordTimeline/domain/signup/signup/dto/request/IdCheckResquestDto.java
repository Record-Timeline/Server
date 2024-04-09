package com.api.RecordTimeline.domain.signup.signup.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@AllArgsConstructor
@RequiredArgsConstructor
public class IdCheckResquestDto {

    @NotNull
    private String memberId;
}
