package com.api.RecordTimeline.domain.mainTimeline.controller;

import com.api.RecordTimeline.domain.mainTimeline.domain.MainTimeline;
import com.api.RecordTimeline.domain.mainTimeline.dto.request.MainTimelineRequestDTO;
import com.api.RecordTimeline.domain.mainTimeline.service.MainTimelineService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/v1/main-timelines")
@RequiredArgsConstructor // 롬복의 RequiredArgsConstructor 어노테이션 사용

public class MainTimelineController {

    private final MainTimelineService mainTimelineService;

    @PostMapping
    public ResponseEntity<MainTimeline> createMainTimeline(@RequestBody MainTimelineRequestDTO mainTimelineRequestDTO) {
        MainTimeline mainTimeline = mainTimelineRequestDTO.toEntity();
        MainTimeline created = mainTimelineService.createMainTimeline(mainTimeline);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    // 멤버 ID로 모든 메인 타임라인 조회
    @GetMapping("/member/{memberId}")
    public ResponseEntity<List<MainTimeline>> getTimelinesByMemberId(@PathVariable Long memberId) {
        try {
            List<MainTimeline> timelines = mainTimelineService.getTimelinesByMemberId(memberId);
            if (timelines.isEmpty()) {
                return ResponseEntity.noContent().build(); // 내용이 없을 경우 No Content 상태 반환
            }
            return ResponseEntity.ok(timelines);
        } catch (RuntimeException ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No timelines found for the member", ex);
        }
    }


//전체 메인타임라인 조회 필요 -> 시간별로 나열 / response만 정리 / 삭제, 수정, 조회 확인 / common package 확인
    @GetMapping("/{id}") //메인타임라인 ID
    public ResponseEntity<MainTimeline> getMainTimelineById(@PathVariable Long id) {
        try {
            MainTimeline timeline = mainTimelineService.getMainTimelineById(id);
            return ResponseEntity.ok(timeline);
        } catch (RuntimeException ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "MainTimeline not found", ex);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<MainTimeline> updateMainTimeline(@PathVariable Long id, @RequestBody MainTimeline mainTimeline) {
        try {
            MainTimeline updated = mainTimelineService.updateMainTimeline(id, mainTimeline);
            return ResponseEntity.ok(updated);
        } catch (RuntimeException ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Could not update MainTimeline", ex);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMainTimeline(@PathVariable Long id) {
        try {
            mainTimelineService.deleteMainTimeline(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Could not delete MainTimeline", ex);
        }
    }

}
