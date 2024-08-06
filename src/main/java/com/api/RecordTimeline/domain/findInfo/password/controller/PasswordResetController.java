package com.api.RecordTimeline.domain.findInfo.password.controller;

import com.api.RecordTimeline.domain.findInfo.password.dto.request.PasswordResetDto;
import com.api.RecordTimeline.domain.findInfo.password.dto.request.PasswordResetRequestDto;
import com.api.RecordTimeline.domain.findInfo.password.service.PasswordResetService;
import com.api.RecordTimeline.domain.signup.email.dto.request.CheckCertificationRequestDto;
import com.api.RecordTimeline.global.success.SuccessResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/password")
public class PasswordResetController {

    private final PasswordResetService passwordResetService;

    @Operation(summary = "비밀번호 재설정 이메일 전송", description = "비밀번호 재설정을 위한 인증번호를 이메일로 전송합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "이메일 전송 성공", content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE)})
    })
    @PostMapping("/reset-email")
    public SuccessResponse<String> sendResetEmail(@Valid @RequestBody PasswordResetRequestDto requestDto) {
        passwordResetService.sendResetEmail(requestDto);
        return new SuccessResponse<>("이메일이 전송되었습니다.");
    }

    @Operation(summary = "인증번호 확인", description = "이메일로 받은 인증번호가 올바른지 확인합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "인증번호 확인 성공", content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE)})
    })
    @PostMapping("/verify-certification")
    public SuccessResponse<String> verifyCertificationNumber(@Valid @RequestBody CheckCertificationRequestDto requestDto) {
        passwordResetService.verifyCertificationNumber(requestDto);
        return new SuccessResponse<>("인증번호가 확인되었습니다.");
    }

    @Operation(summary = "비밀번호 재설정", description = "새로운 비밀번호를 설정합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "비밀번호 재설정 성공", content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE)})
    })
    @PostMapping("/reset")
    public SuccessResponse<String> resetPassword(@Valid @RequestBody PasswordResetDto passwordResetDto) {
        passwordResetService.resetPassword(passwordResetDto);
        return new SuccessResponse<>("비밀번호가 성공적으로 변경되었습니다.");
    }
}
