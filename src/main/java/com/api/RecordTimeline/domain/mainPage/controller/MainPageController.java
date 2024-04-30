package com.api.RecordTimeline.domain.mainPage.controller;

import com.api.RecordTimeline.domain.mainPage.dto.response.MainPageMemberDto;
import com.api.RecordTimeline.domain.mainPage.dto.response.MainPageSubTimelineDto;
import com.api.RecordTimeline.domain.mainPage.service.MainPageServiceImpl;
import com.api.RecordTimeline.domain.member.domain.Interest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/main")
public class MainPageController {

    private final MainPageServiceImpl mainPageService;

    //유저 추천 (사용자 닉네임, 프로필 이미지, 소개글, 메인 타임라인)
    @GetMapping("/member/{interest}")
    public ResponseEntity<List<MainPageMemberDto>> getRecommendMembersByInterest(@PathVariable Interest interest) {
        return mainPageService.recommendMembersByInterest(interest);
    }


    /*
    //게시글 추철 (서브 타임라인 내 게시글)
    @GetMapping("/post/{interest}")
    public ResponseEntity<List<MainPageSubTimelineDto>> getRecommendPostsByInterest(@PathVariable Interest interest) {
        return mainPageService.recommendPostsByInterest(interest);
    }*/
}
