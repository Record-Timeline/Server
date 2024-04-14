package com.api.RecordTimeline.domain.member.service;

import com.api.RecordTimeline.domain.common.ResponseDto;
import com.api.RecordTimeline.domain.member.domain.Member;
import com.api.RecordTimeline.domain.member.dto.request.UpdateMemberRequestDto;
import com.api.RecordTimeline.domain.member.dto.request.UpdatePasswordRequestDto;
import com.api.RecordTimeline.domain.member.dto.response.UpdateResponseDto;
import com.api.RecordTimeline.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UpdatePasswordServiceImpl implements UpdatePasswordService{

    private final MemberRepository memberRepository;
    private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    @Override
    public ResponseEntity<? super UpdateResponseDto> updatePassword(String email, UpdatePasswordRequestDto dto) {
        try {

            Member member = memberRepository.findByEmail(email);

            if (!passwordEncoder.matches(dto.getOldPassword(), member.getPassword())) {
                return UpdateResponseDto.passwordMismatch();
            }

            Member updatedMember = member.updatePassword(dto.getNewPassword(), passwordEncoder);
            memberRepository.save(updatedMember);

        } catch (Exception exception) {
            exception.printStackTrace();
            return ResponseDto.databaseError();
        }

        return UpdateResponseDto.success();
    }
}
