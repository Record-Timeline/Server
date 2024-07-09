package com.api.RecordTimeline.domain.mainPage.service;

import com.api.RecordTimeline.domain.mainPage.dto.response.MainPageMemberDto;
import com.api.RecordTimeline.domain.mainPage.dto.response.MainPageSubTimelineDto;
import com.api.RecordTimeline.domain.mainPage.dto.response.MainTimelineDto;
import com.api.RecordTimeline.domain.mainPage.dto.response.SubtimelineDto;
import com.api.RecordTimeline.domain.mainTimeline.domain.MainTimeline;
import com.api.RecordTimeline.domain.mainTimeline.service.MainTimelineService;
import com.api.RecordTimeline.domain.member.domain.Interest;
import com.api.RecordTimeline.domain.member.domain.Member;
import com.api.RecordTimeline.domain.member.repository.MemberRepository;
import com.api.RecordTimeline.domain.profile.domain.Profile;
import com.api.RecordTimeline.domain.profile.repository.ProfileRepository;
import com.api.RecordTimeline.domain.subTimeline.domain.SubTimeline;
import com.api.RecordTimeline.domain.subTimeline.repository.SubTimelineRepository;
import com.api.RecordTimeline.global.exception.ApiException;
import com.api.RecordTimeline.global.exception.ErrorType;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MainPageServiceImpl implements MainPageService {

    private final MemberRepository memberRepository;
    private final ProfileRepository profileRepository;
    private final MainTimelineService mainTimelineService;
    private final SubTimelineRepository subTimelineRepository;

    @Override
    @Transactional
    public List<MainPageMemberDto> recommendMembersByInterest(Interest interest, Optional<String> loggedInEmail) {
        List<Member> membersWithInterest = loggedInEmail
                .map(email -> memberRepository.findMembersWithSameInterest(interest.toString(), email))
                .orElseGet(() -> memberRepository.findRandomMembersByInterest(interest.toString()));

        if (membersWithInterest.isEmpty()) {
            throw new ApiException(ErrorType._NO_RECOMMENDER_FOUND);
        }

        return membersWithInterest.stream().map(member -> {
            Profile profile = profileRepository.findByMember(member);
            List<MainTimeline> timelines = mainTimelineService.getTimelinesByMemberId(member.getId());
            List<MainTimelineDto> timelineDtos = timelines.stream()
                    .map(timeline -> new MainTimelineDto(
                            timeline.getId(),
                            timeline.getTitle(),
                            timeline.getStartDate(),
                            timeline.getEndDate()))
                    .collect(Collectors.toList());

            return new MainPageMemberDto(
                    member.getId(),
                    member.getNickname(),
                    profile != null ? profile.getProfileImgUrl() : "",
                    profile != null ? profile.getIntroduction() : "",
                    timelineDtos);
        }).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public MainPageSubTimelineDto recommendPostsByInterest(Interest interest, Optional<String> loggedInEmail) {
        List<SubTimeline> subTimelines = loggedInEmail
                .map(email -> subTimelineRepository.findSubTimelinesByInterestExcludingEmail(interest.toString(), email))
                .orElseGet(() -> subTimelineRepository.findSubTimelinesByInterest(interest.toString()));

        if (subTimelines.isEmpty()) {
            throw new ApiException(ErrorType._NO_RECOMMENDER_FOUND);
        }

        List<SubtimelineDto> subTimelineDtos = subTimelines.stream().map(subTimeline -> {
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
                    mainTimeline.getTitle());
        }).collect(Collectors.toList());

        return new MainPageSubTimelineDto(subTimelineDtos);
    }
}
