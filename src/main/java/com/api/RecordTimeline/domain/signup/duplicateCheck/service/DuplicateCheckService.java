package com.api.RecordTimeline.domain.signup.duplicateCheck.service;

import com.api.RecordTimeline.domain.signup.duplicateCheck.dto.request.IdCheckResquestDto;
import com.api.RecordTimeline.domain.signup.duplicateCheck.dto.request.NicknameCheckResquestDto;
import com.api.RecordTimeline.domain.signup.duplicateCheck.dto.response.IdCheckResponseDto;
import com.api.RecordTimeline.domain.signup.duplicateCheck.dto.response.NicknameCheckResponseDto;
import org.springframework.http.ResponseEntity;

public interface DuplicateCheckService {
    ResponseEntity<? super IdCheckResponseDto> idCheck(IdCheckResquestDto dto);
    ResponseEntity<? super NicknameCheckResponseDto> nicknameCheck(NicknameCheckResquestDto dto);
}
