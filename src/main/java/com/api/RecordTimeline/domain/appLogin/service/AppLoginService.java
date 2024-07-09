package com.api.RecordTimeline.domain.appLogin.service;

import com.api.RecordTimeline.domain.appLogin.dto.request.AppLoginRequestDto;
import com.api.RecordTimeline.domain.appLogin.dto.response.AppLoginResponseDto;

public interface AppLoginService {
    AppLoginResponseDto appLogin(AppLoginRequestDto dto);
}
