package com.api.RecordTimeline.domain.signup.signup.service;

import com.api.RecordTimeline.domain.signup.signup.dto.request.BasicSignupRequestDto;
import com.api.RecordTimeline.domain.signup.signup.dto.request.KakaoSignupRequestDto;
import com.api.RecordTimeline.domain.signup.signup.dto.request.UnRegisterRequestDto;
import com.api.RecordTimeline.domain.signup.signup.dto.response.SignupResponseDto;
import com.api.RecordTimeline.domain.signup.signup.dto.response.UnRegisterResponseDto;
import org.springframework.http.ResponseEntity;

public interface SignupService {
    ResponseEntity<? super SignupResponseDto> basicSignup(BasicSignupRequestDto basicDto);
    ResponseEntity<? super SignupResponseDto> kakaoSignup(KakaoSignupRequestDto kakaoDto);
    ResponseEntity<? super UnRegisterResponseDto> unRegister(String email, UnRegisterRequestDto dto);
}
