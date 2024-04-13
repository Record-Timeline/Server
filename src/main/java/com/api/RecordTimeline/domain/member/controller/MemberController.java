package com.api.RecordTimeline.domain.member.controller;

import com.api.RecordTimeline.domain.member.dto.request.UpdateMemberRequestDto;
import com.api.RecordTimeline.domain.member.dto.request.UpdatePasswordRequestDto;
import com.api.RecordTimeline.domain.member.dto.response.UpdateResponseDto;
import com.api.RecordTimeline.domain.member.service.UpdateMemberServiceImpl;
import com.api.RecordTimeline.domain.member.service.UpdatePasswordServiceImpl;
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
    private final UpdateMemberServiceImpl updateMemberService;
    private final UpdatePasswordServiceImpl updatePasswordService;
    @PatchMapping("/update-memberInfo")
    public ResponseEntity<? super UpdateResponseDto> updateMemberInfo(@Valid @RequestBody UpdateMemberRequestDto requestBody) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName(); // 현재 로그인 한 사용자 이메일
        return updateMemberService.updateMemberInfo(email, requestBody);
    }

    @PatchMapping("/update-password")
    public ResponseEntity<? super UpdateResponseDto> updatePassword(@Valid @RequestBody UpdatePasswordRequestDto requestBody) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName(); // 현재 로그인 한 사용자 이메일
        return updatePasswordService.updatePassword(email, requestBody);
    }

}
