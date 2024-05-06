package com.api.RecordTimeline.domain.member.controller;

import com.api.RecordTimeline.domain.member.dto.request.UpdateMemberRequestDto;
import com.api.RecordTimeline.domain.member.dto.request.UpdatePasswordRequestDto;
import com.api.RecordTimeline.domain.member.dto.response.MemberInfoResponseDto;
import com.api.RecordTimeline.domain.member.dto.response.UpdateResponseDto;
import com.api.RecordTimeline.domain.member.service.MemberServiceImpl;
import com.api.RecordTimeline.domain.signup.signup.dto.request.UnRegisterRequestDto;
import com.api.RecordTimeline.domain.signup.signup.dto.response.UnRegisterResponseDto;
import com.api.RecordTimeline.domain.signup.signup.service.SignupServiceImpl;
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

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class MemberController {
    private final MemberServiceImpl memberService;
    private final SignupServiceImpl signupService;
    @PutMapping("/update-memberInfo")
    public ResponseEntity<? super UpdateResponseDto> updateMemberInfo(@Valid @RequestBody UpdateMemberRequestDto requestBody) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName(); // 현재 로그인 한 사용자 이메일
        return memberService.updateMemberInfo(email, requestBody);
    }

    @PutMapping("/update-password")
    public ResponseEntity<? super UpdateResponseDto> updatePassword(@Valid @RequestBody UpdatePasswordRequestDto requestBody) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName(); // 현재 로그인 한 사용자 이메일
        return memberService.updatePassword(email, requestBody);
    }

    @GetMapping("/my-profile")
    public ResponseEntity<MemberInfoResponseDto> getMyProfile() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        return memberService.getUserProfile(email);
    }

    @Operation(summary = "회원 탈퇴", description = "레코드 타임라인에 회원 탈퇴 합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "회원 탈퇴 성공",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = Long.class)))
    })
    @PostMapping("/unRegister")
    public ResponseEntity<? super UnRegisterResponseDto> unRegister (@RequestBody @Valid UnRegisterRequestDto requestBody) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName(); // 현재 로그인 한 사용자 이메일
        return signupService.unRegister(email, requestBody);
    }

}
