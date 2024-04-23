package com.api.RecordTimeline.domain.recommend.controller;

import com.api.RecordTimeline.domain.recommend.dto.RecommendResponseDto;
import com.api.RecordTimeline.domain.recommend.service.RecommendServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/search")
public class RecommendController {

    private final RecommendServiceImpl recommendService;

    @GetMapping
    public ResponseEntity<List<RecommendResponseDto>> getRecommendMember() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        return recommendService.recommendSameInterestMember(email);
    }
}
