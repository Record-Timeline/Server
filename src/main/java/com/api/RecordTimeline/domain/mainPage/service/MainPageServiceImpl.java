package com.api.RecordTimeline.domain.mainPage.service;

import com.api.RecordTimeline.domain.mainPage.dto.response.MainPageMemberDto;
import com.api.RecordTimeline.domain.mainPage.dto.response.MainPageSubTimelineDto;
import com.api.RecordTimeline.domain.member.domain.Interest;
import com.api.RecordTimeline.domain.member.domain.Member;
import com.api.RecordTimeline.domain.member.repository.MemberRepository;
import com.api.RecordTimeline.domain.profile.domain.Profile;
import com.api.RecordTimeline.domain.profile.repository.ProfileRepository;
import com.api.RecordTimeline.global.exception.ApiException;
import com.api.RecordTimeline.global.exception.ErrorType;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MainPageServiceImpl implements MainPageService {

    private final MemberRepository memberRepository;
    private final ProfileRepository profileRepository;

    @Transactional
    public ResponseEntity<List<MainPageMemberDto>> recommendMembersByInterest(Interest interest) {
        List<Member> membersWithInterest = memberRepository.findRandomMembersByInterest(interest.toString());
        if (membersWithInterest.isEmpty()) {
            throw new ApiException(ErrorType._NO_RECOMMENDER_FOUND);
        }

        List<MainPageMemberDto> responseDtos = membersWithInterest.stream().map(member -> {
            Profile profile = profileRepository.findByMember(member);
            MainPageMemberDto dto = new MainPageMemberDto(
                    member.getNickname(),
                    profile != null ? profile.getProfileImgUrl() : "",
                    profile != null ? profile.getIntroduction() : "",
                    null // 메인 타임라인 구현 후 추가
            );
            return dto;
        }).collect(Collectors.toList());

        return ResponseEntity.ok(responseDtos);
    }

    /** 서브 타임라인 구현 후 구현 시작 **/

    /*
    @Override
    public ResponseEntity<List<MainPageSubTimelineDto>> recommendPostsByInterest(Interest interest) {
        return null;
    }
    */
}
