package com.api.RecordTimeline.domain.mainPage.controller;

import com.api.RecordTimeline.domain.mainPage.dto.response.MainPageMemberDto;
import com.api.RecordTimeline.domain.mainPage.dto.response.MainPageSubTimelineDto;
import com.api.RecordTimeline.domain.mainPage.service.MainPageService;
import com.api.RecordTimeline.domain.member.domain.Interest;
import com.api.RecordTimeline.global.success.SuccessResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/main")
public class MainPageController {

    private final MainPageService mainPageService;

    @GetMapping("/member/{interest}")
    public ResponseEntity<SuccessResponse<List<MainPageMemberDto>>> getRecommendMembersByInterest(@PathVariable Interest interest) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Optional<String> email = Optional.ofNullable(authentication)
                .filter(auth -> !(auth instanceof AnonymousAuthenticationToken))
                .map(Authentication::getName);
        List<MainPageMemberDto> recommendedMembers = mainPageService.recommendMembersByInterest(interest, email);
        return ResponseEntity.ok(new SuccessResponse<>(recommendedMembers));
    }

    @GetMapping("/post/{interest}")
    public ResponseEntity<SuccessResponse<MainPageSubTimelineDto>> getRecommendPostsByInterest(@PathVariable Interest interest) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Optional<String> email = Optional.ofNullable(authentication)
                .filter(auth -> !(auth instanceof AnonymousAuthenticationToken))
                .map(Authentication::getName);
        MainPageSubTimelineDto recommendedPosts = mainPageService.recommendPostsByInterest(interest, email);
        return ResponseEntity.ok(new SuccessResponse<>(recommendedPosts));
    }
}
