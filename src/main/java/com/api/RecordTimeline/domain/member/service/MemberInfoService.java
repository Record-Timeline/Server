//package com.api.RecordTimeline.domain.member.service;
//import com.api.RecordTimeline.domain.member.dto.response.MemberInfoResponseDto;
//import com.api.RecordTimeline.domain.member.repository.MemberRepository;
//import com.api.RecordTimeline.domain.profile.domain.Profile;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Service;
//
//import java.util.Optional;
//
//@Service
//@RequiredArgsConstructor
//public class MemberInfoService {
//    private final MemberRepository memberRepository;
//    private Profile profile;
//
//    public MemberInfoResponseDto getUserProfile(String email) {
//        return Optional.ofNullable(memberRepository.findByEmailAndIsDeletedFalse(email))
//                .map(member -> {
//                    String profileImgUrl = Optional.ofNullable(member.getProfile()).map(Profile::getProfileImgUrl).orElse(null);
//                    String introduction = Optional.ofNullable(member.getProfile()).map(Profile::getIntroduction).orElse(null);
//                    return MemberInfoResponseDto.builder()
//                            .nickname(member.getNickname())
//                            .interest(member.getInterest().toString())
//                            .profileImageUrl(profileImgUrl)
//                            .introduction(introduction)
//                            .build();
//                })
//                .orElseThrow(() -> new RuntimeException("Member not found"));
//    }
//
//}
