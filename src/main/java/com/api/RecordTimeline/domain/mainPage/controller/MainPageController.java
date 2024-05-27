package com.api.RecordTimeline.domain.mainPage.controller;

import com.api.RecordTimeline.domain.mainPage.dto.response.MainPageMemberDto;
import com.api.RecordTimeline.domain.mainPage.dto.response.MainPageSubTimelineDto;
import com.api.RecordTimeline.domain.mainPage.service.MainPageServiceImpl;
import com.api.RecordTimeline.domain.member.domain.Interest;
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

    private final MainPageServiceImpl mainPageService;

    //유저 추천 (사용자 닉네임, 프로필 이미지, 소개글, 메인 타임라인)
    @GetMapping("/member/{interest}")
    public ResponseEntity<List<MainPageMemberDto>> getRecommendMembersByInterest(@PathVariable Interest interest) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || authentication instanceof AnonymousAuthenticationToken) {
            // 로그인하지 않은 경우 모든 유저를 대상으로 추천
            return mainPageService.recommendMembersByInterest(interest, Optional.empty());
        } else {
            // 로그인한 경우 로그인한 유저를 제외하고 추천
            String email = authentication.getName();
            return mainPageService.recommendMembersByInterest(interest, Optional.of(email));
        }
    }



    //게시글 추천 (서브 타임라인 내 게시글)
    @GetMapping("/post/{interest}")
    public ResponseEntity<List<MainPageSubTimelineDto>> getRecommendPostsByInterest(@PathVariable Interest interest) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || authentication instanceof AnonymousAuthenticationToken) {
            // 로그인하지 않은 경우 모든 유저를 대상으로 추천
            return mainPageService.recommendPostsByInterest(interest, Optional.empty());
        } else {
            // 로그인한 경우 로그인한 유저를 제외하고 추천
            String email = authentication.getName();
            return mainPageService.recommendPostsByInterest(interest, Optional.of(email));
        }
    }
}
