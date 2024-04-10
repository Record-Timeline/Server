package com.api.RecordTimeline.domain.signup.duplicateCheck.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@AllArgsConstructor
@RequiredArgsConstructor
public class IdCheckResquestDto {

    @Schema(description = "사용자 아이디", example = "사용자 아이디 예시")
    @NotNull
    private String memberId;
}
