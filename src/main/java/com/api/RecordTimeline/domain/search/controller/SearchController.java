package com.api.RecordTimeline.domain.search.controller;

import com.api.RecordTimeline.domain.member.dto.response.MemberInfoResponseDto;
import com.api.RecordTimeline.domain.search.dto.response.SearchPageRecommendDto;
import com.api.RecordTimeline.domain.search.dto.response.SearchResultDto;
import com.api.RecordTimeline.domain.search.dto.response.SearchSubTimelineDto;
import com.api.RecordTimeline.domain.search.service.SearchServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/search")
public class SearchController {

    private final SearchServiceImpl searchService;

    @GetMapping
    public ResponseEntity<List<SearchPageRecommendDto>> getRecommendMember() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        return searchService.recommendSameInterestMember(email);
    }

    @GetMapping("/keyword")
    public ResponseEntity<SearchResultDto> searchAll(@RequestParam String keyword) {
        SearchResultDto searchResult = searchService.searchAllByKeyword(keyword);
        return ResponseEntity.ok(searchResult);
    }
}
