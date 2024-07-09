package com.api.RecordTimeline.domain.signup.signup.controller;

import com.api.RecordTimeline.domain.signup.duplicateCheck.dto.request.EmailCheckResquestDto;
import com.api.RecordTimeline.domain.signup.duplicateCheck.dto.request.NicknameCheckResquestDto;
import com.api.RecordTimeline.domain.signup.duplicateCheck.service.DuplicateCheckService;
import com.api.RecordTimeline.domain.signup.email.dto.request.CheckCertificationRequestDto;
import com.api.RecordTimeline.domain.signup.email.dto.request.EmailCertificationRequestDto;
import com.api.RecordTimeline.domain.signup.email.service.EmailService;
import com.api.RecordTimeline.domain.signup.signup.dto.request.BasicSignupRequestDto;
import com.api.RecordTimeline.domain.signup.signup.dto.request.KakaoSignupRequestDto;
import com.api.RecordTimeline.domain.signup.signup.dto.request.UnRegisterRequestDto;
import com.api.RecordTimeline.domain.signup.signup.service.SignupService;
import com.api.RecordTimeline.global.success.SuccessResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

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
                    schema = @Schema(implementation = String.class)))
    })
    @PostMapping("/email-check")
    public ResponseEntity<SuccessResponse<Void>> emailCheck (@RequestBody @Valid EmailCheckResquestDto requestBody) {
        duplicateCheckService.emailCheck(requestBody);
        return ResponseEntity.ok(new SuccessResponse<>(null));
    }

    @Operation(summary = "닉네임 중복 확인", description = "닉네임 중복 확인을 합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "닉네임 중복 확인 성공",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = String.class)))
    })
    @PostMapping("/nickname-check")
    public ResponseEntity<SuccessResponse<Void>> nicknameCheck (@RequestBody @Valid NicknameCheckResquestDto requestBody) {
        duplicateCheckService.nicknameCheck(requestBody);
        return ResponseEntity.ok(new SuccessResponse<>(null));
    }

    @Operation(summary = "자체 서비스 회원가입", description = "레코드 타임라인 자체 서비스 회원가입을 합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "자체 서비스 회원가입 성공",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = String.class)))
    })
    @PostMapping("/basic-signup")
    public ResponseEntity<SuccessResponse<String>> basicSignup (@RequestBody @Valid BasicSignupRequestDto requestBody) {
        String token = signupService.basicSignup(requestBody);
        return ResponseEntity.ok(new SuccessResponse<>(token));
    }

    @Operation(summary = "카카오 소셜 회원가입", description = "카카오 소셜 회원가입을 합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "카카오 소셜 회원가입 성공",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = String.class)))
    })
    @PostMapping("/kakao-signup")
    public ResponseEntity<SuccessResponse<String>> kakaoSignup (@RequestBody @Valid KakaoSignupRequestDto requestBody) {
        String token = signupService.kakaoSignup(requestBody);
        return ResponseEntity.ok(new SuccessResponse<>(token));
    }

    @Operation(summary = "이메일 인증 메일 전송", description = "이메일 인증 메일을 전송합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "이메일 인증 메일 전송 성공",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = String.class)))
    })
    @PostMapping("/email-certification")
    public ResponseEntity<SuccessResponse<Void>> emailCertification (@RequestBody @Valid EmailCertificationRequestDto requestBody) {
        emailService.emailCertification(requestBody);
        return ResponseEntity.ok(new SuccessResponse<>(null));
    }

    @Operation(summary = "이메일 인증 번호 확인", description = "이메일 인증 번호가 일치하는지 확인합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "이메일 인증 번호 확인 성공",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = String.class)))
    })
    @PostMapping("/check-certification")
    public ResponseEntity<SuccessResponse<Void>> checkCertification (@RequestBody @Valid CheckCertificationRequestDto requestBody) {
        emailService.checkCertification(requestBody);
        return ResponseEntity.ok(new SuccessResponse<>(null));
    }

    @Operation(summary = "회원 탈퇴", description = "레코드 타임라인에 회원 탈퇴 합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "회원 탈퇴 성공",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = String.class)))
    })
    @PostMapping("/unregister")
    public ResponseEntity<SuccessResponse<Void>> unRegister (@RequestBody @Valid UnRegisterRequestDto requestBody) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName(); // 현재 로그인 한 사용자 이메일
        signupService.unRegister(email, requestBody);
        return ResponseEntity.ok(new SuccessResponse<>(null));
    }
}
