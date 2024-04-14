package com.api.RecordTimeline.domain.appLogin.service;

import com.api.RecordTimeline.domain.appLogin.dto.request.AppLoginRequestDto;
import com.api.RecordTimeline.domain.appLogin.dto.response.AppLoginResponseDto;
import org.springframework.http.ResponseEntity;


public interface AppLoginService {
    ResponseEntity<? super AppLoginResponseDto> appLogin(AppLoginRequestDto dto);
}
