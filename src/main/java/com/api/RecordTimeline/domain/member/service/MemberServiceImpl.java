package com.api.RecordTimeline.domain.member.service;

import com.api.RecordTimeline.domain.common.ResponseDto;
import com.api.RecordTimeline.domain.member.domain.Member;
import com.api.RecordTimeline.domain.member.dto.request.UpdateMemberRequestDto;
import com.api.RecordTimeline.domain.member.dto.request.UpdatePasswordRequestDto;
import com.api.RecordTimeline.domain.member.dto.response.MemberIdResponseDto;
import com.api.RecordTimeline.domain.member.dto.response.MemberInfoResponseDto;
import com.api.RecordTimeline.domain.member.dto.response.UpdateResponseDto;
import com.api.RecordTimeline.domain.member.repository.MemberRepository;
import com.api.RecordTimeline.domain.profile.domain.Profile;
import com.api.RecordTimeline.global.exception.ApiException;
import com.api.RecordTimeline.global.exception.ApiExceptionResponse;
import com.api.RecordTimeline.global.exception.ErrorType;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService{
    private final MemberRepository memberRepository;
    private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    @Override
    public ResponseEntity<? super UpdateResponseDto> updateMemberInfo(String email, UpdateMemberRequestDto dto) {
        try {

            Member member = memberRepository.findByEmailAndIsDeletedFalse(email);

            String nickname = dto.getNewNickname();
            boolean isExistNickname = memberRepository.existsByNicknameAndIsDeletedFalse(nickname);
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

    @Override
    public ResponseEntity<? super UpdateResponseDto> updatePassword(String email, UpdatePasswordRequestDto dto) {
        try {

            Member member = memberRepository.findByEmailAndIsDeletedFalse(email);

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

    @Override
    public ResponseEntity<MemberInfoResponseDto> getUserProfile(String email) {
        try {
            Member member = memberRepository.findByEmailAndIsDeletedFalse(email);
            if (member == null) {
                throw new ApiException(ErrorType._USER_NOT_FOUND_DB);
            }

            Profile profile = member.getProfile();
            if (profile == null) {
                throw new ApiException(ErrorType._NO_PROFILE_FOUND);
            }

            MemberInfoResponseDto responseDto = MemberInfoResponseDto.fromMemberAndProfile(member, profile);
            return ResponseEntity.ok(responseDto);

        } catch (ApiException  e) {
            throw  e;

        } catch (Exception exception) {
            throw new ApiException(ErrorType._DATABASE_ERROR);
        }
    }

    public ResponseEntity<MemberInfoResponseDto> getProfileByMemberId(Long memberId) {
        Member member = memberRepository.findByIdAndIsDeletedFalse(memberId);
        if (member == null) {
            throw new ApiException(ErrorType._USER_NOT_FOUND_DB);
        }

        Profile profile = member.getProfile();
        if (profile == null) {
            throw new ApiException(ErrorType._NO_PROFILE_FOUND);
        }

        MemberInfoResponseDto responseDto = MemberInfoResponseDto.fromMemberAndProfile(member, profile);
        return ResponseEntity.ok(responseDto);
    }

    // 이메일로 memberId 조회
    @Override
    public ResponseEntity<MemberIdResponseDto> getMemberIdByEmail(String email) {
        Member member = memberRepository.findByEmailAndIsDeletedFalse(email);
        if (member == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new MemberIdResponseDto(null));
        }
        return ResponseEntity.ok(new MemberIdResponseDto(member.getId()));
    }

    @Override
    public List<MemberInfoResponseDto> getAllMembers() {
        List<Member> members = memberRepository.findAllByIsDeletedFalse();
        return members.stream()
                .map(member -> MemberInfoResponseDto.fromMemberAndProfile(member, member.getProfile()))
                .toList();
    }
}
