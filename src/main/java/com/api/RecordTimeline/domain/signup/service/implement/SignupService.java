package com.api.RecordTimeline.domain.signup.service.implement;

import com.api.RecordTimeline.domain.signup.dto.request.BasicSignupRequestDto;
import com.api.RecordTimeline.domain.signup.dto.request.EmailCheckResquestDto;
import com.api.RecordTimeline.domain.signup.dto.request.KakaoSignupRequestDto;
import com.api.RecordTimeline.domain.signup.dto.request.NicknameCheckResquestDto;
import com.api.RecordTimeline.domain.signup.dto.response.EmailCheckResponseDto;
import com.api.RecordTimeline.domain.signup.dto.response.NicknameCheckResponseDto;
import com.api.RecordTimeline.domain.signup.dto.response.SignupResponseDto;
import org.springframework.http.ResponseEntity;

public interface SignupService {
    ResponseEntity<? super EmailCheckResponseDto> emailCheck(EmailCheckResquestDto dto);
    ResponseEntity<? super NicknameCheckResponseDto> nicknameCheck(NicknameCheckResquestDto dto);
    ResponseEntity<? super SignupResponseDto> basicSignup(BasicSignupRequestDto basicDto);
    ResponseEntity<? super SignupResponseDto> kakaoSignup(KakaoSignupRequestDto kakaoDto);

}
