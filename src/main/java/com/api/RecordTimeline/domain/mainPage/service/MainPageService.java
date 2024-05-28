package com.api.RecordTimeline.domain.mainPage.service;

import com.api.RecordTimeline.domain.mainPage.dto.response.MainPageMemberDto;
import com.api.RecordTimeline.domain.mainPage.dto.response.MainPageSubTimelineDto;
import com.api.RecordTimeline.domain.member.domain.Interest;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

public interface MainPageService {
    ResponseEntity<List<MainPageMemberDto>> recommendMembersByInterest(Interest interest, Optional<String> loggedInEmail);

    ResponseEntity<MainPageSubTimelineDto> recommendPostsByInterest(Interest interest, Optional<String> loggedInEmail);
}
