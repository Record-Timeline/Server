package com.api.RecordTimeline.domain.member.service;


import com.api.RecordTimeline.domain.member.dto.request.UpdateMemberRequestDto;
import com.api.RecordTimeline.domain.member.dto.response.UpdateMemberResponseDto;
import org.springframework.http.ResponseEntity;

public interface MemberService {
    ResponseEntity<? super UpdateMemberResponseDto> updateMember(String email, UpdateMemberRequestDto dto);
}
