package com.api.RecordTimeline.domain.signup.duplicateCheck.service;

import com.api.RecordTimeline.domain.member.repository.MemberRepository;
import com.api.RecordTimeline.domain.signup.duplicateCheck.dto.request.EmailCheckResquestDto;
import com.api.RecordTimeline.domain.signup.duplicateCheck.dto.request.NicknameCheckResquestDto;
import com.api.RecordTimeline.global.exception.ApiException;
import com.api.RecordTimeline.global.exception.ErrorType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DuplicateCheckServiceImpl implements DuplicateCheckService {
    private final MemberRepository memberRepository;

    @Override
    public void emailCheck(EmailCheckResquestDto dto) {
        String email = dto.getEmail();
        boolean isExistEmail = memberRepository.existsByEmailAndIsDeletedFalse(email);
        if (isExistEmail) {
            throw new ApiException(ErrorType._DUPLICATE_EMAIL);
        }
    }

    @Override
    public void nicknameCheck(NicknameCheckResquestDto dto) {
        String nickname = dto.getNickname();
        boolean isExistNickname = memberRepository.existsByNicknameAndIsDeletedFalse(nickname);
        if (isExistNickname) {
            throw new ApiException(ErrorType._DUPLICATE_NICKNAME);
        }
    }
}
