package com.api.RecordTimeline.domain.signup.duplicateCheck.service;

import com.api.RecordTimeline.domain.member.repository.MemberRepository;
import com.api.RecordTimeline.domain.signup.duplicateCheck.dto.request.EmailCheckResquestDto;
import com.api.RecordTimeline.domain.signup.duplicateCheck.dto.request.NicknameCheckResquestDto;
import com.api.RecordTimeline.domain.signup.duplicateCheck.dto.response.EmailCheckResponseDto;
import com.api.RecordTimeline.domain.signup.duplicateCheck.dto.response.NicknameCheckResponseDto;
import com.api.RecordTimeline.domain.common.ResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DuplicateCheckServiceImpl implements DuplicateCheckService{
    private final MemberRepository memberRepository;
    @Override
    public ResponseEntity<? super EmailCheckResponseDto> emailCheck(EmailCheckResquestDto dto) {

        try{
            String email = dto.getEmail();
            boolean isExistEmail = memberRepository.existsByEmail(email);
            if(isExistEmail)
                return EmailCheckResponseDto.duplicateEmail();

        } catch (Exception exception){
            exception.printStackTrace();
            return ResponseDto.databaseError();
        }
        return EmailCheckResponseDto.success();
    }

    @Override
    public ResponseEntity<? super NicknameCheckResponseDto> nicknameCheck(NicknameCheckResquestDto dto) {

        try{
            String nickname = dto.getNickname();
            boolean isExistNickname = memberRepository.existsByNickname(nickname);
            if(isExistNickname)
                return NicknameCheckResponseDto.duplicateNickname();

        } catch (Exception exception){
            exception.printStackTrace();
            return ResponseDto.databaseError();
        }
        return NicknameCheckResponseDto.success();
    }
}


