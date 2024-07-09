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
import com.api.RecordTimeline.domain.signup.signup.dto.request.UnRegisterRequestDto;
import com.api.RecordTimeline.domain.signup.signup.dto.response.UnRegisterResponseDto;
import com.api.RecordTimeline.global.exception.ApiException;
import com.api.RecordTimeline.global.exception.ErrorType;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Override
    public UpdateResponseDto updateMemberInfo(String email, UpdateMemberRequestDto dto) {
        Member member = memberRepository.findByEmailAndIsDeletedFalse(email)
                .orElseThrow(() -> new ApiException(ErrorType._USER_NOT_FOUND_DB));

        String nickname = dto.getNewNickname();
        boolean isExistNickname = memberRepository.existsByNicknameAndIsDeletedFalse(nickname);
        if (isExistNickname) {
            throw new ApiException(ErrorType._DUPLICATE_NICKNAME);
        }

        member.update(dto.getNewNickname(), dto.getNewInterest());
        memberRepository.save(member);

        return new UpdateResponseDto();
    }

    @Override
    public UpdateResponseDto updatePassword(String email, UpdatePasswordRequestDto dto) {
        Member member = memberRepository.findByEmailAndIsDeletedFalse(email)
                .orElseThrow(() -> new ApiException(ErrorType._USER_NOT_FOUND_DB));

        if (!passwordEncoder.matches(dto.getOldPassword(), member.getPassword())) {
            throw new ApiException(ErrorType._PASSWORD_MISMATCH);
        }

        Member updatedMember = member.updatePassword(dto.getNewPassword(), passwordEncoder);
        memberRepository.save(updatedMember);

        return new UpdateResponseDto();
    }

    @Override
    public MemberInfoResponseDto getUserProfile(String email) {
        Member member = memberRepository.findByEmailAndIsDeletedFalse(email)
                .orElseThrow(() -> new ApiException(ErrorType._USER_NOT_FOUND_DB));

        Profile profile = Optional.ofNullable(member.getProfile())
                .orElseThrow(() -> new ApiException(ErrorType._NO_PROFILE_FOUND));

        return MemberInfoResponseDto.fromMemberAndProfile(member, profile);
    }

    @Override
    public MemberInfoResponseDto getProfileByMemberId(Long memberId) {
        Member member = memberRepository.findByIdAndIsDeletedFalse(memberId)
                .orElseThrow(() -> new ApiException(ErrorType._USER_NOT_FOUND_DB));

        Profile profile = Optional.ofNullable(member.getProfile())
                .orElseThrow(() -> new ApiException(ErrorType._NO_PROFILE_FOUND));

        return MemberInfoResponseDto.fromMemberAndProfile(member, profile);
    }

    @Override
    public MemberIdResponseDto getMemberIdByEmail(String email) {
        Member member = memberRepository.findByEmailAndIsDeletedFalse(email)
                .orElseThrow(() -> new ApiException(ErrorType._USER_NOT_FOUND_DB));

        return new MemberIdResponseDto(member.getId());
    }

    @Override
    public List<MemberInfoResponseDto> getAllMembers() {
        List<Member> members = memberRepository.findAllByIsDeletedFalse();
        return members.stream()
                .map(member -> MemberInfoResponseDto.fromMemberAndProfile(member, member.getProfile()))
                .toList();
    }

    @Override
    public UnRegisterResponseDto unRegister(String email, UnRegisterRequestDto dto) {
        Member member = memberRepository.findByEmailAndIsDeletedFalse(email)
                .orElseThrow(() -> new ApiException(ErrorType._USER_NOT_FOUND_DB));

        member.markAsDeleted();
        memberRepository.save(member);

        return new UnRegisterResponseDto();
    }
}
