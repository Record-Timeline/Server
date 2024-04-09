package com.api.RecordTimeline.domain.signup.signup.controller;

import com.api.RecordTimeline.domain.signup.duplicateCheck.service.DuplicateCheckService;
import com.api.RecordTimeline.domain.signup.email.dto.request.CheckCertificationRequestDto;
import com.api.RecordTimeline.domain.signup.email.dto.request.EmailCertificationRequestDto;
import com.api.RecordTimeline.domain.signup.email.dto.response.CheckCertificationResponseDto;
import com.api.RecordTimeline.domain.signup.email.dto.response.EmailCertificationResponseDto;
import com.api.RecordTimeline.domain.signup.email.service.EmailService;
import com.api.RecordTimeline.domain.signup.signup.dto.request.BasicSignupRequestDto;
import com.api.RecordTimeline.domain.signup.duplicateCheck.dto.request.IdCheckResquestDto;
import com.api.RecordTimeline.domain.signup.signup.dto.request.KakaoSignupRequestDto;
import com.api.RecordTimeline.domain.signup.duplicateCheck.dto.request.NicknameCheckResquestDto;
import com.api.RecordTimeline.domain.signup.duplicateCheck.dto.response.IdCheckResponseDto;
import com.api.RecordTimeline.domain.signup.duplicateCheck.dto.response.NicknameCheckResponseDto;
import com.api.RecordTimeline.domain.signup.signup.dto.response.SignupResponseDto;
import com.api.RecordTimeline.domain.signup.signup.service.SignupService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
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
    private final DuplicateCheckService duplicateCheckService;

    @Operation(summary = "아이디 중복 확인", description = "아이디 중복 확인을 합니다.")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "아이디 중복 확인 성공",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = Long.class)))
    })
    @PostMapping("/id-check")
    public ResponseEntity<? super IdCheckResponseDto> emailCheck (@RequestBody @Valid IdCheckResquestDto requestBody) {
        ResponseEntity<? super IdCheckResponseDto> response = duplicateCheckService.idCheck(requestBody);
        return response;
    }

    @Operation(summary = "닉네임 중복 확인", description = "닉네임 중복 확인을 합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "닉네임 중복 확인 성공",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = Long.class)))
    })
    @PostMapping("/nickname-check")
    public ResponseEntity<? super NicknameCheckResponseDto> nicknameCheck (@RequestBody @Valid NicknameCheckResquestDto requestBody) {
        ResponseEntity<? super NicknameCheckResponseDto> response = duplicateCheckService.nicknameCheck(requestBody);
        return response;
    }

    @Operation(summary = "자체 서비스 회원가입", description = "레코드 타임라인 자체 서비스 회원가입을 합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "자체 서비스 회원가입 성공",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = Long.class)))
    })
    @PostMapping("/basic-signup")
    public ResponseEntity<? super SignupResponseDto> basicSignup (@RequestBody @Valid BasicSignupRequestDto requestBody) {
        ResponseEntity<? super SignupResponseDto> response = signupService.basicSignup(requestBody);
        return response;
    }

    @Operation(summary = "카카오 소셜 회원가입", description = "카카오 소셜 회원가입을 합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "카카오 소셜 회원가입 성공",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = Long.class)))
    })
    @PostMapping("/kakao-signup")
    public ResponseEntity<? super SignupResponseDto> kakaoSignup (@RequestBody @Valid KakaoSignupRequestDto requestBody) {
        ResponseEntity<? super SignupResponseDto> response = signupService.kakaoSignup(requestBody);
        return response;
    }

    @Operation(summary = "이메일 인증 메일 전송", description = "이메일 인증 메일을 전송합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "이메일 인증 메일 전송 성공",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = Long.class)))
    })
    @PostMapping("/email-certification")
    public ResponseEntity<? super EmailCertificationResponseDto> emailCertification (@RequestBody @Valid EmailCertificationRequestDto requestBody) {
        ResponseEntity<? super EmailCertificationResponseDto> response = emailService.emailCertification(requestBody);
        return response;
    }

    @Operation(summary = "이메일 인증 번호 확인", description = "이메일 인증 번호가 일치하는지 확인합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "이메일 인증 번호 확인 성공",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = Long.class)))
    })
    @PostMapping("/check-certification")
    public ResponseEntity<? super CheckCertificationResponseDto> checkCertification (@RequestBody @Valid CheckCertificationRequestDto requestBody) {
        ResponseEntity<? super CheckCertificationResponseDto> response = emailService.checkCertification(requestBody);
        return response;
    }
}
