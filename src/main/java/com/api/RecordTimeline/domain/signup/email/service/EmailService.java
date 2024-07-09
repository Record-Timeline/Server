package com.api.RecordTimeline.domain.signup.email.service;

import com.api.RecordTimeline.domain.signup.email.dto.request.CheckCertificationRequestDto;
import com.api.RecordTimeline.domain.signup.email.dto.request.EmailCertificationRequestDto;

public interface EmailService {
    void emailCertification(EmailCertificationRequestDto dto);
    void checkCertification(CheckCertificationRequestDto dto);
}
