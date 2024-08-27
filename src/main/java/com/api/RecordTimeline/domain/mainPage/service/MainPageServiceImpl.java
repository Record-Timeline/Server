package com.api.RecordTimeline.domain.mainPage.service;

import com.api.RecordTimeline.domain.follow.service.FollowService;
import com.api.RecordTimeline.domain.mainPage.dto.response.MainPageMemberDto;
import com.api.RecordTimeline.domain.mainPage.dto.response.MainPageSubTimelineDto;
import com.api.RecordTimeline.domain.mainPage.dto.response.MainTimelineDto;
import com.api.RecordTimeline.domain.mainPage.dto.response.SubtimelineDto;
import com.api.RecordTimeline.domain.mainTimeline.domain.MainTimeline;
import com.api.RecordTimeline.domain.mainTimeline.service.MainTimelineService;
import com.api.RecordTimeline.domain.member.domain.Interest;
import com.api.RecordTimeline.domain.member.domain.Member;
import com.api.RecordTimeline.domain.member.dto.response.MemberInfoResponseDto;
import com.api.RecordTimeline.domain.member.repository.MemberRepository;
import com.api.RecordTimeline.domain.profile.domain.Profile;
import com.api.RecordTimeline.domain.profile.repository.ProfileRepository;
import com.api.RecordTimeline.domain.subTimeline.domain.SubTimeline;
import com.api.RecordTimeline.domain.subTimeline.repository.SubTimelineRepository;
import com.api.RecordTimeline.global.exception.ApiException;
import com.api.RecordTimeline.global.exception.ErrorType;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MainPageServiceImpl implements MainPageService {

    private final MemberRepository memberRepository;
    private final ProfileRepository profileRepository;
    private final MainTimelineService mainTimelineService;
    private final SubTimelineRepository subTimelineRepository;
    private final FollowService followService;


    // 관심사를 기반으로 회원 추천 기능
    @Transactional
    public ResponseEntity<List<MainPageMemberDto>> recommendMembersByInterest(Interest interest, Optional<String> loggedInEmail) {
        List<Member> membersWithInterest;

        // 로그인된 이메일이 있는 경우, 해당 이메일을 제외하고 같은 관심사의 회원을 찾음
        if (loggedInEmail.isPresent()) {
            membersWithInterest = memberRepository.findMembersWithSameInterest(interest.toString(), loggedInEmail.get());
        } else {
            // 로그인된 이메일이 없는 경우, 랜덤으로 같은 관심사의 회원을 찾음
            membersWithInterest = memberRepository.findRandomMembersByInterest(interest.toString());
        }

        // 추천할 회원이 없는 경우 예외 발생
        if (membersWithInterest.isEmpty()) {
            throw new ApiException(ErrorType._NO_RECOMMENDER_FOUND);
        }

        // 회원 정보를 DTO로 변환
        List<MainPageMemberDto> responseDtos = membersWithInterest.stream().map(member -> {
            Profile profile = profileRepository.findByMember(member);
            List<MainTimeline> timelines = mainTimelineService.getTimelinesByMemberId(member.getId());
            List<MainTimelineDto> timelineDtos = timelines.stream().map(timeline -> new MainTimelineDto(
                    timeline.getId(),
                    timeline.getTitle(),
                    timeline.getStartDate(),
                    timeline.getEndDate(),
                    timeline.isPrivate(),
                    timeline.isDone()
            )).collect(Collectors.toList());

            Long followCount = followService.getFollowerCountForMember(member.getId());

            MainPageMemberDto dto = new MainPageMemberDto(
                    member.getId(),
                    member.getNickname(),
                    profile != null ? profile.getProfileImgUrl() : "",
                    profile != null ? profile.getIntroduction() : "",
                    timelineDtos,
                    followCount
            );
            return dto;
        }).collect(Collectors.toList());

        return ResponseEntity.ok(responseDtos);
    }

    // 관심사를 기반으로 서브 타임라인 추천 기능
    @Override
    public ResponseEntity<MainPageSubTimelineDto> recommendPostsByInterest(Interest interest, Optional<String> loggedInEmail) {
        List<SubTimeline> subTimelines;

        // 로그인된 이메일이 있는 경우, 해당 이메일을 제외하고 같은 관심사의 서브 타임라인을 찾음
        if (loggedInEmail.isPresent()) {
            subTimelines = subTimelineRepository.findSubTimelinesByInterestExcludingEmail(interest.toString(), loggedInEmail.get());
        } else {
            // 로그인된 이메일이 없는 경우, 랜덤으로 같은 관심사의 서브 타임라인을 찾음
            subTimelines = subTimelineRepository.findSubTimelinesByInterest(interest.toString());
        }

        // 추천할 서브 타임라인이 없는 경우 예외 발생
        if (subTimelines.isEmpty()) {
            throw new ApiException(ErrorType._NO_RECOMMENDER_FOUND);
        }

        // 서브 타임라인 정보를 DTO로 변환
        List<SubtimelineDto> subTimelineDtos = subTimelines.stream()
                .map(subTimeline -> {
                    Member member = subTimeline.getMainTimeline().getMember();
                    MainTimeline mainTimeline = subTimeline.getMainTimeline();
                    return new SubtimelineDto(
                            subTimeline.getId(),
                            subTimeline.getMainTimeline().getId(),
                            subTimeline.getTitle(),
                            subTimeline.getContent(),
                            subTimeline.getStartDate(),
                            subTimeline.getEndDate(),
                            member.getId(),
                            member.getNickname(),
                            member.getInterest().toString(),
                            mainTimeline.getTitle()
                    );
                })
                .collect(Collectors.toList());

        MainPageSubTimelineDto responseDto = new MainPageSubTimelineDto(subTimelineDtos);

        return ResponseEntity.ok(responseDto);
    }
}

