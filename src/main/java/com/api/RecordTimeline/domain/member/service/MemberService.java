package com.api.RecordTimeline.domain.member.service;

import com.api.RecordTimeline.domain.member.dto.request.UpdateMemberRequestDto;
import com.api.RecordTimeline.domain.member.dto.request.UpdatePasswordRequestDto;
import com.api.RecordTimeline.domain.member.dto.response.MemberIdResponseDto;
import com.api.RecordTimeline.domain.member.dto.response.MemberInfoResponseDto;
import com.api.RecordTimeline.domain.member.dto.response.UpdateResponseDto;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface MemberService {
    ResponseEntity<? super UpdateResponseDto> updateMemberInfo(String email, UpdateMemberRequestDto dto);
    ResponseEntity<? super UpdateResponseDto> updatePassword(String email, UpdatePasswordRequestDto dto);
    ResponseEntity<MemberInfoResponseDto> getUserProfile(String email);
    ResponseEntity<MemberIdResponseDto> getMemberIdByEmail(String email);
    List<MemberInfoResponseDto> getAllMembers();
}
