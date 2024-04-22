package com.api.RecordTimeline.domain.member.controller;

import com.api.RecordTimeline.domain.member.dto.request.UpdateMemberRequestDto;
import com.api.RecordTimeline.domain.member.dto.request.UpdatePasswordRequestDto;
import com.api.RecordTimeline.domain.member.dto.response.MemberInfoResponseDto;
import com.api.RecordTimeline.domain.member.dto.response.UpdateResponseDto;
import com.api.RecordTimeline.domain.member.service.MemberServiceImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class MemberController {
    private final MemberServiceImpl memberService;
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


}
