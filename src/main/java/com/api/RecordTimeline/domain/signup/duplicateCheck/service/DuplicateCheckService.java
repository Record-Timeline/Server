package com.api.RecordTimeline.domain.signup.duplicateCheck.service;

import com.api.RecordTimeline.domain.signup.duplicateCheck.dto.request.EmailCheckResquestDto;
import com.api.RecordTimeline.domain.signup.duplicateCheck.dto.request.NicknameCheckResquestDto;
import com.api.RecordTimeline.domain.signup.duplicateCheck.dto.response.EmailCheckResponseDto;
import com.api.RecordTimeline.domain.signup.duplicateCheck.dto.response.NicknameCheckResponseDto;
import org.springframework.http.ResponseEntity;

public interface DuplicateCheckService {
    ResponseEntity<? super EmailCheckResponseDto> emailCheck(EmailCheckResquestDto dto);
    ResponseEntity<? super NicknameCheckResponseDto> nicknameCheck(NicknameCheckResquestDto dto);
}
