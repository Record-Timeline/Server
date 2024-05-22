package com.api.RecordTimeline.domain.search.service;

import com.api.RecordTimeline.domain.member.dto.response.MemberInfoResponseDto;
import com.api.RecordTimeline.domain.search.dto.response.SearchPageRecommendDto;
import com.api.RecordTimeline.domain.search.dto.response.SearchResultDto;
import com.api.RecordTimeline.domain.search.dto.response.SearchSubTimelineDto;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface SearchService {
    ResponseEntity<List<SearchPageRecommendDto>> recommendSameInterestMember(String email);

    List<MemberInfoResponseDto> searchMembersByKeyword(String keyword);

    List<SearchSubTimelineDto> searchSubTimelinesByKeyword(String keyword);

    SearchResultDto searchAllByKeyword(String keyword);
}
