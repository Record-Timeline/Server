package com.api.RecordTimeline.domain.search.service;

import com.api.RecordTimeline.domain.member.domain.Member;
import com.api.RecordTimeline.domain.member.dto.response.MemberInfoResponseDto;
import com.api.RecordTimeline.domain.member.repository.MemberRepository;
import com.api.RecordTimeline.domain.profile.domain.Profile;
import com.api.RecordTimeline.domain.profile.repository.ProfileRepository;
import com.api.RecordTimeline.domain.search.dto.response.SearchPageRecommendDto;
import com.api.RecordTimeline.domain.search.dto.response.SearchResultDto;
import com.api.RecordTimeline.domain.search.dto.response.SearchSubTimelineDto;
import com.api.RecordTimeline.domain.subTimeline.domain.SubTimeline;
import com.api.RecordTimeline.domain.subTimeline.repository.SubTimelineRepository;
import com.api.RecordTimeline.global.exception.ApiException;
import com.api.RecordTimeline.global.exception.ErrorType;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SearchServiceImpl implements SearchService {

    private static final Logger logger = LoggerFactory.getLogger(SearchServiceImpl.class);

    private final MemberRepository memberRepository;
    private final ProfileRepository profileRepository;
    private final SubTimelineRepository subTimelineRepository;

    @Transactional
    @Override
    public ResponseEntity<List<SearchPageRecommendDto>> recommendSameInterestMember(String email) {
        Member member = memberRepository.findByEmailAndIsDeletedFalse(email);
        if (member == null) {
            throw new ApiException(ErrorType._USER_NOT_FOUND_DB);
        }

        List<Member> membersWithSameInterest = memberRepository.findMembersWithSameInterest(member.getInterest().name(), email);
        if (membersWithSameInterest.isEmpty()) {
            throw new ApiException(ErrorType._NO_RECOMMENDER_FOUND);
        }

        List<SearchPageRecommendDto> result = membersWithSameInterest.stream().map(m -> {
            Profile profile = profileRepository.findByMember(m);
            return new SearchPageRecommendDto(
                    m.getNickname(),
                    profile != null ? profile.getProfileImgUrl() : "",
                    profile != null ? profile.getIntroduction() : ""
            );
        }).collect(Collectors.toList());

        return ResponseEntity.ok(result);
    }

    @Override
    public List<MemberInfoResponseDto> searchMembersByKeyword(String keyword) {
        List<Member> searchMember = memberRepository.findByNicknameContainingOrProfileIntroductionContaining(keyword);
        if (searchMember.isEmpty()) {
            throw new ApiException(ErrorType._NO_SEARCH_RESULTS);
        }

        return searchMember.stream().map(member -> new MemberInfoResponseDto(
                member.getNickname(),
                member.getInterest().toString(),
                Optional.ofNullable(member.getProfile().getProfileImgUrl()).orElse(""),
                Optional.ofNullable(member.getProfile().getIntroduction()).orElse("")
        )).collect(Collectors.toList());
    }

    @Override
    public List<SearchSubTimelineDto> searchSubTimelinesByKeyword(String keyword) {
        List<SubTimeline> subTimelines = subTimelineRepository.findByTitleOrContentContaining(keyword);
        if (subTimelines.isEmpty()) {
            throw new ApiException(ErrorType._NO_SEARCH_RESULTS);
        }

        return subTimelines.stream().map(subTimeline -> {
            Member author = subTimeline.getMainTimeline().getMember();
            return new SearchSubTimelineDto(
                    subTimeline.getId(),
                    subTimeline.getTitle(),
                    subTimeline.getContent(),
                    subTimeline.getStartDate(),
                    subTimeline.getEndDate(),
                    subTimeline.getImageUrls().isEmpty() ? null : subTimeline.getImageUrls().get(0),
                    author.getNickname(),
                    author.getInterest().name()
            );
        }).collect(Collectors.toList());
    }

    @Override
    public SearchResultDto searchAllByKeyword(String keyword) {
        List<MemberInfoResponseDto> members = searchMembersByKeyword(keyword);

        List<SearchSubTimelineDto> subTimelines = searchSubTimelinesByKeyword(keyword);

        if (members.isEmpty() && subTimelines.isEmpty()) {
            throw new ApiException(ErrorType._NO_SEARCH_RESULTS);
        }

        return new SearchResultDto(members, subTimelines);
    }
}
