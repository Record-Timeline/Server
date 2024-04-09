package com.api.RecordTimeline.domain.signup.signup.controller;

import com.api.RecordTimeline.domain.signup.email.dto.request.CheckCertificationRequestDto;
import com.api.RecordTimeline.domain.signup.email.dto.request.EmailCertificationRequestDto;
import com.api.RecordTimeline.domain.signup.email.dto.response.CheckCertificationResponseDto;
import com.api.RecordTimeline.domain.signup.email.dto.response.EmailCertificationResponseDto;
import com.api.RecordTimeline.domain.signup.email.service.EmailService;
import com.api.RecordTimeline.domain.signup.signup.dto.request.BasicSignupRequestDto;
import com.api.RecordTimeline.domain.signup.signup.dto.request.IdCheckResquestDto;
import com.api.RecordTimeline.domain.signup.signup.dto.request.KakaoSignupRequestDto;
import com.api.RecordTimeline.domain.signup.signup.dto.request.NicknameCheckResquestDto;
import com.api.RecordTimeline.domain.signup.signup.dto.response.IdCheckResponseDto;
import com.api.RecordTimeline.domain.signup.signup.dto.response.NicknameCheckResponseDto;
import com.api.RecordTimeline.domain.signup.signup.dto.response.SignupResponseDto;
import com.api.RecordTimeline.domain.signup.signup.service.SignupService;
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
    private final EmailService emailService;

    @PostMapping("/id-check")
    public ResponseEntity<? super IdCheckResponseDto> emailCheck (@RequestBody @Valid IdCheckResquestDto requestBody) {
        ResponseEntity<? super IdCheckResponseDto> response = signupService.idCheck(requestBody);
        return response;
    }
    @PostMapping("/nickname-check")
    public ResponseEntity<? super NicknameCheckResponseDto> nicknameCheck (@RequestBody @Valid NicknameCheckResquestDto requestBody) {
        ResponseEntity<? super NicknameCheckResponseDto> response = signupService.nicknameCheck(requestBody);
        return response;
    }

    @PostMapping("/basic-signup")
    public ResponseEntity<? super SignupResponseDto> basicSignup (@RequestBody @Valid BasicSignupRequestDto requestBody) {
        ResponseEntity<? super SignupResponseDto> response = signupService.basicSignup(requestBody);
        return response;
    }

    @PostMapping("/kakao-signup")
    public ResponseEntity<? super SignupResponseDto> kakaoSignup (@RequestBody @Valid KakaoSignupRequestDto requestBody) {
        ResponseEntity<? super SignupResponseDto> response = signupService.kakaoSignup(requestBody);
        return response;
    }

    @PostMapping("/email-certification")
    public ResponseEntity<? super EmailCertificationResponseDto> emailCertification (@RequestBody @Valid EmailCertificationRequestDto requestBody) {
        ResponseEntity<? super EmailCertificationResponseDto> response = emailService.emailCertification(requestBody);
        return response;
    }

    @PostMapping("/check-certification")
    public ResponseEntity<? super CheckCertificationResponseDto> checkCertification (@RequestBody @Valid CheckCertificationRequestDto requestBody) {
        ResponseEntity<? super CheckCertificationResponseDto> response = emailService.checkCertification(requestBody);
        return response;
    }
}
