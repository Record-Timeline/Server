package com.api.RecordTimeline.domain.member.service;

import com.api.RecordTimeline.domain.common.ResponseDto;
import com.api.RecordTimeline.domain.member.domain.Member;
import com.api.RecordTimeline.domain.member.dto.request.UpdateMemberRequestDto;
import com.api.RecordTimeline.domain.member.dto.response.UpdateMemberResponseDto;
import com.api.RecordTimeline.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService{

    private final MemberRepository memberRepository;

    @Override
    public ResponseEntity<? super UpdateMemberResponseDto> updateMember(String email, UpdateMemberRequestDto dto) {
            try {
                Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
                String loggedInMemberEmail = authentication.getName(); // 현재 로그인 한 사용자 이메일

                if (!email.equals(loggedInMemberEmail)) {
                    return UpdateMemberResponseDto.notAuthorized();
                }

                Member member = memberRepository.findByEmail(email);

                if (member == null) {
                    return UpdateMemberResponseDto.memberNotFound();
                }

                String nickname = dto.getNewNickname();
                boolean isExistNickname = memberRepository.existsByNickname(nickname);
                if (isExistNickname)
                    return UpdateMemberResponseDto.duplicateNickname();

                member.update(dto.getNewNickname(), dto.getNewInterest());
                memberRepository.save(member);

            } catch (Exception exception) {
                exception.printStackTrace();
                return ResponseDto.databaseError();
            }

            return UpdateMemberResponseDto.success();
    }
}

