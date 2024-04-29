//package com.api.RecordTimeline.domain.recommend.service;
//
//import com.api.RecordTimeline.domain.member.domain.Interest;
//import com.api.RecordTimeline.domain.member.domain.Member;
//import com.api.RecordTimeline.domain.member.repository.MemberRepository;
//import com.api.RecordTimeline.domain.profile.domain.Profile;
//import com.api.RecordTimeline.domain.profile.repository.ProfileRepository;
//import com.api.RecordTimeline.domain.recommend.dto.RecommendResponseDto;
//import com.api.RecordTimeline.global.exception.ApiException;
//import com.api.RecordTimeline.global.exception.ErrorType;
//import jakarta.transaction.Transactional;
//import lombok.RequiredArgsConstructor;
//import org.springframework.http.ResponseEntity;
//import org.springframework.stereotype.Service;
//
//import java.util.Collections;
//import java.util.List;
//import java.util.stream.Collectors;
//
//@Service
//@RequiredArgsConstructor
//public class RecommendServiceImpl implements RecommendService {
//
//    private final MemberRepository memberRepository;
//    private final ProfileRepository profileRepository;
//
//    @Transactional
//    @Override
//    public ResponseEntity<List<RecommendResponseDto>> recommendSameInterestMember(String email) {
//        Member member = memberRepository.findByEmailAndIsDeletedFalse(email);
//        if (member == null) {
//            throw new ApiException(ErrorType._USER_NOT_FOUND_DB);
//        }
//
//        List<Member> membersWithSameInterest = memberRepository.findMembersWithSameInterest(member.getInterest(), email);
//        Collections.shuffle(membersWithSameInterest);
//        List<Member> selectedMembers = membersWithSameInterest.stream().limit(5).collect(Collectors.toList());
//
//        List<RecommendResponseDto> result = selectedMembers.stream().map(m -> {
//            Profile profile = profileRepository.findByMemberAndIsDeletedFalse(m);
//            return new RecommendResponseDto(
//                    m.getNickname(),
//                    profile != null ? profile.getProfileImgUrl() : "",
//                    profile != null ? profile.getIntroduction() : ""
//            );
//        }).collect(Collectors.toList());
//
//        return ResponseEntity.ok(result);
//    }
//}
