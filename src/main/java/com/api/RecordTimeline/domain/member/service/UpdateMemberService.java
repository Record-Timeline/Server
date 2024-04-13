package com.api.RecordTimeline.domain.member.service;


import com.api.RecordTimeline.domain.member.dto.request.UpdateMemberRequestDto;
import com.api.RecordTimeline.domain.member.dto.response.UpdateResponseDto;
import org.springframework.http.ResponseEntity;

public interface UpdateMemberService {
    ResponseEntity<? super UpdateResponseDto> updateMemberInfo(String email, UpdateMemberRequestDto dto);
}
