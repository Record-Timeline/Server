package com.api.RecordTimeline.domain.search.service;

import com.api.RecordTimeline.domain.follow.service.FollowService;
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
    private final FollowService followService;

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
            String profileImageUrl = "";
            String introduction = "";

            if (profile != null) {
                profileImageUrl = profile.getProfileImgUrl();
                introduction = profile.getIntroduction();
            }

            return new SearchPageRecommendDto(
                    m.getNickname(),
                    profileImageUrl,
                    introduction
            );
        }).collect(Collectors.toList());

        return ResponseEntity.ok(result);
    }

    @Override
    public List<MemberInfoResponseDto> searchMembersByKeyword(String keyword) {
        // 키워드를 기반으로 닉네임 또는 프로필 소개글에 해당 키워드를 포함하는 회원을 검색
        List<Member> searchMember = memberRepository.findByNicknameContainingOrProfileIntroductionContaining(keyword);

        // 검색 결과가 없으면 빈 리스트를 반환
        if (searchMember.isEmpty()) {
            return List.of();
        }

        // 검색된 회원 정보를 DTO로 변환하여 반환
        return searchMember.stream()
                .map(member -> {
                    Long followerCount = followService.getFollowerCountForMember(member.getId());
                    Long followingCount = followService.getFollowingCountForMember(member.getId());
                    return MemberInfoResponseDto.fromMemberAndProfile(member, member.getProfile(), followerCount, followingCount);
                })
                .collect(Collectors.toList());
    }


    @Override
    public List<SearchSubTimelineDto> searchSubTimelinesByKeyword(String keyword) {

        // 키워드를 기반으로 제목 또는 내용에 해당 키워드를 포함하는 서브 타임라인을 검색
        List<SubTimeline> subTimelines = subTimelineRepository.findByTitleOrContentContaining(keyword);

        // 검색 결과가 없으면 빈 리스트를 반환
        if (subTimelines.isEmpty()) {
            return List.of();
        }

        // 검색된 서브 타임라인 정보를 DTO로 변환하여 반환
        return subTimelines.stream().map(subTimeline -> {
            Member author = subTimeline.getMainTimeline().getMember();
            return new SearchSubTimelineDto(
                    subTimeline.getId(),
                    subTimeline.getMainTimeline().getId(),
                    subTimeline.getTitle(),
                    subTimeline.getContent(),
                    subTimeline.getStartDate(),
                    subTimeline.getEndDate(),
                    author.getId(),
                    author.getNickname(),
                    author.getInterest().name()
            );
        }).collect(Collectors.toList());
    }

    @Override
    public SearchResultDto searchAllByKeyword(String keyword) {
        // 키워드를 기반으로 회원 검색
        List<MemberInfoResponseDto> members = searchMembersByKeyword(keyword);

        // 키워드를 기반으로 서브 타임라인 검색
        List<SearchSubTimelineDto> subTimelines = searchSubTimelinesByKeyword(keyword);

        // 검색 결과가 모두 없으면 예외 발생
        if (members.isEmpty() && subTimelines.isEmpty()) {
            throw new ApiException(ErrorType._NO_SEARCH_RESULTS);
        }

        // 검색 결과를 DTO로 변환하여 반환
        return new SearchResultDto(members, subTimelines);
    }
}
