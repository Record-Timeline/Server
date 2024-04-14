package com.api.RecordTimeline.domain.member.service;

import com.api.RecordTimeline.domain.common.ResponseDto;
import com.api.RecordTimeline.domain.member.domain.Member;
import com.api.RecordTimeline.domain.member.dto.request.UpdateMemberRequestDto;
import com.api.RecordTimeline.domain.member.dto.response.UpdateResponseDto;
import com.api.RecordTimeline.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UpdateMemberServiceImpl implements UpdateMemberService {

    private final MemberRepository memberRepository;

    @Override
    public ResponseEntity<? super UpdateResponseDto> updateMemberInfo(String email, UpdateMemberRequestDto dto) {
        try {

            Member member = memberRepository.findByEmail(email);

            String nickname = dto.getNewNickname();
            boolean isExistNickname = memberRepository.existsByNickname(nickname);
            if (isExistNickname)
                return UpdateResponseDto.duplicateNickname();

            member.update(dto.getNewNickname(), dto.getNewInterest());
            memberRepository.save(member);

        } catch (Exception exception) {
            exception.printStackTrace();
            return ResponseDto.databaseError();
        }

        return UpdateResponseDto.success();
    }
}

