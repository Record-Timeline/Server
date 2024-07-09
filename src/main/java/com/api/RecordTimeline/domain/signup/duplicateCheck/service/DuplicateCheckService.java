package com.api.RecordTimeline.domain.signup.duplicateCheck.service;


import com.api.RecordTimeline.domain.signup.duplicateCheck.dto.request.EmailCheckResquestDto;
import com.api.RecordTimeline.domain.signup.duplicateCheck.dto.request.NicknameCheckResquestDto;

public interface DuplicateCheckService {
    void emailCheck(EmailCheckResquestDto dto);
    void nicknameCheck(NicknameCheckResquestDto dto);
}
