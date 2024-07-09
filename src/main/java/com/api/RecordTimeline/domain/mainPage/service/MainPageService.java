package com.api.RecordTimeline.domain.mainPage.service;

import com.api.RecordTimeline.domain.mainPage.dto.response.MainPageMemberDto;
import com.api.RecordTimeline.domain.mainPage.dto.response.MainPageSubTimelineDto;
import com.api.RecordTimeline.domain.member.domain.Interest;

import java.util.List;
import java.util.Optional;

public interface MainPageService {
    List<MainPageMemberDto> recommendMembersByInterest(Interest interest, Optional<String> loggedInEmail);
    MainPageSubTimelineDto recommendPostsByInterest(Interest interest, Optional<String> loggedInEmail);
}
