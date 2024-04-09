package com.api.RecordTimeline.domain.signup.controller;

import com.api.RecordTimeline.domain.signup.dto.request.BasicSignupRequestDto;
import com.api.RecordTimeline.domain.signup.dto.request.EmailCheckResquestDto;
import com.api.RecordTimeline.domain.signup.dto.request.KakaoSignupRequestDto;
import com.api.RecordTimeline.domain.signup.dto.request.NicknameCheckResquestDto;
import com.api.RecordTimeline.domain.signup.dto.response.EmailCheckResponseDto;
import com.api.RecordTimeline.domain.signup.dto.response.NicknameCheckResponseDto;
import com.api.RecordTimeline.domain.signup.dto.response.SignupResponseDto;
import com.api.RecordTimeline.domain.signup.service.implement.SignupService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/auth")
public class SignupController {
    private final SignupService signupService;
    @PostMapping("/email-check")
    public ResponseEntity<? super EmailCheckResponseDto> emailCheck (@RequestBody @Valid EmailCheckResquestDto requestBody) {
        ResponseEntity<? super EmailCheckResponseDto> response = signupService.emailCheck(requestBody);
        return response;
    }
    @PostMapping("/nickname-check")
    public ResponseEntity<? super NicknameCheckResponseDto> nicknameCheck (@RequestBody @Valid NicknameCheckResquestDto requestBody) {
        ResponseEntity<? super NicknameCheckResponseDto> response = signupService.nicknameCheck(requestBody);
        return response;
    }

    @PostMapping("/basic-signup")
    public ResponseEntity<? super BasicSignupRequestDto> basicSignup (@RequestBody @Valid BasicSignupRequestDto requestBody) {
        ResponseEntity<? super SignupResponseDto> response = signupService.basicSignup(requestBody);
        return (ResponseEntity<? super BasicSignupRequestDto>) response;
    }

    @PostMapping("/kakao-signup")
    public ResponseEntity<? super SignupResponseDto> kakaoSignup (@RequestBody @Valid KakaoSignupRequestDto requestBody) {
        ResponseEntity<? super SignupResponseDto> response = signupService.kakaoSignup(requestBody);
        return response;
    }
}
