package com.api.RecordTimeline.domain.member.service;

import com.api.RecordTimeline.domain.member.dto.request.UpdateMemberRequestDto;
import com.api.RecordTimeline.domain.member.dto.request.UpdatePasswordRequestDto;
import com.api.RecordTimeline.domain.member.dto.response.MemberIdResponseDto;
import com.api.RecordTimeline.domain.member.dto.response.MemberInfoResponseDto;
import com.api.RecordTimeline.domain.member.dto.response.UpdateResponseDto;
import com.api.RecordTimeline.domain.signup.signup.dto.request.UnRegisterRequestDto;
import com.api.RecordTimeline.domain.signup.signup.dto.response.UnRegisterResponseDto;

import java.util.List;

public interface MemberService {
    UpdateResponseDto updateMemberInfo(String email, UpdateMemberRequestDto dto);
    UpdateResponseDto updatePassword(String email, UpdatePasswordRequestDto dto);
    MemberInfoResponseDto getUserProfile(String email);
    MemberInfoResponseDto getProfileByMemberId(Long memberId);
    MemberIdResponseDto getMemberIdByEmail(String email);
    List<MemberInfoResponseDto> getAllMembers();
    UnRegisterResponseDto unRegister(String email, UnRegisterRequestDto dto);
}
