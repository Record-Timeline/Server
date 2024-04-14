package com.api.RecordTimeline.domain.signup.email.service;

import com.api.RecordTimeline.domain.signup.email.dto.request.CheckCertificationRequestDto;
import com.api.RecordTimeline.domain.signup.email.dto.request.EmailCertificationRequestDto;
import com.api.RecordTimeline.domain.signup.email.dto.response.CheckCertificationResponseDto;
import com.api.RecordTimeline.domain.signup.email.dto.response.EmailCertificationResponseDto;
import org.springframework.http.ResponseEntity;

public interface EmailService {
    ResponseEntity<? super EmailCertificationResponseDto> emailCertification(EmailCertificationRequestDto dto);
    ResponseEntity<? super CheckCertificationResponseDto> checkCertification(CheckCertificationRequestDto dto);
}
