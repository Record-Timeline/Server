package com.api.RecordTimeline.domain.signup.signup.service;

import com.api.RecordTimeline.domain.signup.signup.dto.request.BasicSignupRequestDto;
import com.api.RecordTimeline.domain.signup.signup.dto.request.IdCheckResquestDto;
import com.api.RecordTimeline.domain.signup.signup.dto.request.KakaoSignupRequestDto;
import com.api.RecordTimeline.domain.signup.signup.dto.request.NicknameCheckResquestDto;
import com.api.RecordTimeline.domain.signup.signup.dto.response.IdCheckResponseDto;
import com.api.RecordTimeline.domain.signup.signup.dto.response.NicknameCheckResponseDto;
import com.api.RecordTimeline.domain.signup.signup.dto.response.SignupResponseDto;
import org.springframework.http.ResponseEntity;

public interface SignupService {
    ResponseEntity<? super IdCheckResponseDto> idCheck(IdCheckResquestDto dto);
    ResponseEntity<? super NicknameCheckResponseDto> nicknameCheck(NicknameCheckResquestDto dto);
    ResponseEntity<? super SignupResponseDto> basicSignup(BasicSignupRequestDto basicDto);
    ResponseEntity<? super SignupResponseDto> kakaoSignup(KakaoSignupRequestDto kakaoDto);

}
