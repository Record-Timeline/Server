package com.api.RecordTimeline.domain.signup.signup.service;

import com.api.RecordTimeline.domain.signup.signup.dto.request.BasicSignupRequestDto;
import com.api.RecordTimeline.domain.signup.signup.dto.request.KakaoSignupRequestDto;
import com.api.RecordTimeline.domain.signup.signup.dto.request.UnRegisterRequestDto;
import com.api.RecordTimeline.domain.signup.signup.dto.response.UnRegisterResponseDto;

public interface SignupService {
    String basicSignup(BasicSignupRequestDto basicDto);
    String kakaoSignup(KakaoSignupRequestDto kakaoDto);
    UnRegisterResponseDto unRegister(String email, UnRegisterRequestDto dto);
}
