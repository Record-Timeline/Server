package com.api.RecordTimeline.domain.member.controller;

import com.api.RecordTimeline.domain.member.dto.request.UpdateMemberRequestDto;
import com.api.RecordTimeline.domain.member.dto.request.UpdatePasswordRequestDto;
import com.api.RecordTimeline.domain.member.dto.response.MemberIdResponseDto;
import com.api.RecordTimeline.domain.member.dto.response.MemberInfoResponseDto;
import com.api.RecordTimeline.domain.member.dto.response.UpdateResponseDto;
import com.api.RecordTimeline.domain.member.service.MemberService;
import com.api.RecordTimeline.domain.signup.signup.dto.request.UnRegisterRequestDto;
import com.api.RecordTimeline.domain.signup.signup.dto.response.UnRegisterResponseDto;
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

import java.util.List;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;
    private final SignupService signupService;

    @PutMapping("/update-memberInfo")
    public ResponseEntity<SuccessResponse<UpdateResponseDto>> updateMemberInfo(@Valid @RequestBody UpdateMemberRequestDto requestBody) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName(); // 현재 로그인 한 사용자 이메일
        UpdateResponseDto response = memberService.updateMemberInfo(email, requestBody);
        return ResponseEntity.ok(new SuccessResponse<>(response));
    }

    @PutMapping("/update-password")
    public ResponseEntity<SuccessResponse<UpdateResponseDto>> updatePassword(@Valid @RequestBody UpdatePasswordRequestDto requestBody) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName(); // 현재 로그인 한 사용자 이메일
        UpdateResponseDto response = memberService.updatePassword(email, requestBody);
        return ResponseEntity.ok(new SuccessResponse<>(response));
    }

    @GetMapping("/my-profile")
    public ResponseEntity<SuccessResponse<MemberInfoResponseDto>> getMyProfile() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        MemberInfoResponseDto response = memberService.getUserProfile(email);
        return ResponseEntity.ok(new SuccessResponse<>(response));
    }

    @GetMapping("/profile/{memberId}")
    public ResponseEntity<SuccessResponse<MemberInfoResponseDto>> getProfileByMemberId(@PathVariable Long memberId) {
        MemberInfoResponseDto response = memberService.getProfileByMemberId(memberId);
        return ResponseEntity.ok(new SuccessResponse<>(response));
    }

    @GetMapping("/member-id")
    public ResponseEntity<SuccessResponse<MemberIdResponseDto>> getMemberIdByEmail(@RequestParam String email) {
        MemberIdResponseDto response = memberService.getMemberIdByEmail(email);
        return ResponseEntity.ok(new SuccessResponse<>(response));
    }

    @GetMapping("/members")
    public ResponseEntity<SuccessResponse<List<MemberInfoResponseDto>>> getAllMembers() {
        List<MemberInfoResponseDto> members = memberService.getAllMembers();
        return ResponseEntity.ok(new SuccessResponse<>(members));
    }

    @Operation(summary = "회원 탈퇴", description = "레코드 타임라인에 회원 탈퇴 합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "회원 탈퇴 성공",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = SuccessResponse.class)))
    })
    @PostMapping("/unregister")
    public ResponseEntity<SuccessResponse<UnRegisterResponseDto>> unRegister(@RequestBody @Valid UnRegisterRequestDto requestBody) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName(); // 현재 로그인 한 사용자 이메일
        UnRegisterResponseDto response = signupService.unRegister(email, requestBody);
        return ResponseEntity.ok(new SuccessResponse<>(response));
    }
}
