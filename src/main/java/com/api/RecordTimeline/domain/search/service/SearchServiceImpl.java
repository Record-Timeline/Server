package com.api.RecordTimeline.domain.search.service;

import com.api.RecordTimeline.domain.member.domain.Member;
import com.api.RecordTimeline.domain.member.repository.MemberRepository;
import com.api.RecordTimeline.domain.profile.domain.Profile;
import com.api.RecordTimeline.domain.profile.repository.ProfileRepository;
import com.api.RecordTimeline.domain.search.dto.response.SearchPageRecommendDto;
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
public class SearchServiceImpl implements SearchService {

    private final MemberRepository memberRepository;
    private final ProfileRepository profileRepository;

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
}
