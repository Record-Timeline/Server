package com.api.RecordTimeline.domain.signup.signup.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@AllArgsConstructor
@RequiredArgsConstructor
public class NicknameCheckResquestDto {

    @NotBlank
    private String nickname;
}
