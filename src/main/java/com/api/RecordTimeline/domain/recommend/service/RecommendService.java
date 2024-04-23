package com.api.RecordTimeline.domain.recommend.service;

import com.api.RecordTimeline.domain.recommend.dto.RecommendResponseDto;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface RecommendService {
    ResponseEntity<List<RecommendResponseDto>> recommendSameInterestMember(String email);
}
