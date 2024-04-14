package com.api.RecordTimeline.domain.member.service;

import com.api.RecordTimeline.domain.member.dto.request.UpdateMemberRequestDto;
import com.api.RecordTimeline.domain.member.dto.request.UpdatePasswordRequestDto;
import com.api.RecordTimeline.domain.member.dto.response.UpdateResponseDto;
import org.springframework.http.ResponseEntity;

public interface UpdatePasswordService {
    ResponseEntity<? super UpdateResponseDto> updatePassword(String email, UpdatePasswordRequestDto dto);
}
