package com.api.RecordTimeline.domain.mainPage.service;

import com.api.RecordTimeline.domain.mainPage.dto.response.MainPageMemberDto;
import com.api.RecordTimeline.domain.mainPage.dto.response.MainPageSubTimelineDto;
import com.api.RecordTimeline.domain.mainPage.dto.response.MainTimelineDto;
import com.api.RecordTimeline.domain.mainTimeline.domain.MainTimeline;
import com.api.RecordTimeline.domain.mainTimeline.dto.response.ReadResponseDTO;
import com.api.RecordTimeline.domain.mainTimeline.service.MainTimelineService;
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
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MainPageServiceImpl implements MainPageService {

    private final MemberRepository memberRepository;
    private final ProfileRepository profileRepository;
    private final MainTimelineService mainTimelineService;

    @Transactional
    public ResponseEntity<List<MainPageMemberDto>> recommendMembersByInterest(Interest interest, Optional<String> loggedInEmail) {
        List<Member> membersWithInterest;
        if (loggedInEmail.isPresent()) {
            membersWithInterest = memberRepository.findMembersWithSameInterest(interest.toString(), loggedInEmail.get());
        } else {
            membersWithInterest = memberRepository.findRandomMembersByInterest(interest.toString());
        }
        if (membersWithInterest.isEmpty()) {
            throw new ApiException(ErrorType._NO_RECOMMENDER_FOUND);
        }


        List<MainPageMemberDto> responseDtos = membersWithInterest.stream().map(member -> {
            Profile profile = profileRepository.findByMember(member);
            List<MainTimeline> timelines = mainTimelineService.getTimelinesByMemberId(member.getId());
            List<MainTimelineDto> timelineDtos = timelines.stream().map(timeline -> new MainTimelineDto(
                    timeline.getId(),
                    timeline.getTitle(),
                    timeline.getStartDate(),
                    timeline.getEndDate()
            )).collect(Collectors.toList());

            MainPageMemberDto dto = new MainPageMemberDto(
                    member.getNickname(),
                    profile != null ? profile.getProfileImgUrl() : "",
                    profile != null ? profile.getIntroduction() : "",
                    timelineDtos
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
