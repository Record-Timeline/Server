package com.api.RecordTimeline.domain.subTimeline.controller;

import com.api.RecordTimeline.domain.subTimeline.domain.SubTimeline;
import com.api.RecordTimeline.domain.subTimeline.dto.request.SubTimelineCreateRequestDTO;
import com.api.RecordTimeline.domain.subTimeline.dto.request.UpdateSubTimelineRequestDTO;
import com.api.RecordTimeline.domain.subTimeline.dto.response.*;
import com.api.RecordTimeline.domain.subTimeline.service.SubTimelineService;
import com.api.RecordTimeline.global.exception.ApiException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/sub-timelines")
public class SubTimelineController {

    private final SubTimelineService subTimelineService;

    @PostMapping
    public ResponseEntity<SubCreateResponseDTO> createSubTimeline(@RequestBody SubTimelineCreateRequestDTO request) {
        try {
            SubTimeline created = subTimelineService.createSubTimeline(request);
            return ResponseEntity.status(HttpStatus.CREATED).body(SubCreateResponseDTO.success(created.getId()));
        } catch (ApiException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(SubCreateResponseDTO.failure("Creation/Update Failed", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(SubCreateResponseDTO.failure("Creation/Update Failed", e.getMessage()));
        }
    }

    // 전체 조회 API (날짜 기준 정렬, 비공개 포함)
    @GetMapping("/main/{mainTimelineId}/ordered")
    public ResponseEntity<SubReadResponseDTO> getSubTimelinesByMainTimelineIdOrderByStartDate(@PathVariable Long mainTimelineId) {
        try {
            SubReadResponseDTO response = subTimelineService.getAllSubTimelinesOrdered(mainTimelineId);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }


    @PutMapping("/{subTimelineId}")
    public ResponseEntity<SubUpdateResponseDTO> updateSubTimeline(@PathVariable Long subTimelineId, @RequestBody UpdateSubTimelineRequestDTO request) {
        try {
            SubTimeline updated = subTimelineService.updateSubTimeline(subTimelineId, request);
            return ResponseEntity.ok(SubUpdateResponseDTO.success());
        } catch (ApiException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(SubUpdateResponseDTO.failure());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(SubUpdateResponseDTO.failure());
        }
    }

    @DeleteMapping("/{subTimelineId}")
    public ResponseEntity<SubDeleteResponseDTO> deleteSubTimeline(@PathVariable Long subTimelineId) {
        try {
            subTimelineService.deleteSubTimeline(subTimelineId);
            return ResponseEntity.ok(SubDeleteResponseDTO.success());
        } catch (ApiException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(SubDeleteResponseDTO.failure());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(SubDeleteResponseDTO.failure());
        }
    }

    @GetMapping("/{subTimelineId}/like-bookmark")
    public ResponseEntity<SubTimelineWithLikeBookmarkDTO> getSubTimelineWithLikeAndBookmark(@PathVariable Long subTimelineId) {
        SubTimelineWithLikeBookmarkDTO dto = subTimelineService.getSubTimelineWithLikeAndBookmark(subTimelineId);
        return ResponseEntity.ok(dto);
    }

    // 서브타임라인 공개/비공개 설정 API
    @PutMapping("/{subTimelineId}/privacy")
    public ResponseEntity<SubPrivacyUpdateResponseDTO> setSubTimelinePrivacy(@PathVariable Long subTimelineId, @RequestParam boolean isPrivate) {
        SubPrivacyUpdateResponseDTO response = subTimelineService.setSubTimelinePrivacy(subTimelineId, isPrivate);
        return ResponseEntity.ok(response);
    }

    // 사용자 본인의 서브타임라인 조회 API
    @GetMapping("/my")
    public ResponseEntity<List<SubMyTimelineResponseDTO>> getMySubTimelines() {
        List<SubMyTimelineResponseDTO> mySubTimelines = subTimelineService.getMySubTimelinesWithCommentAndReplyCounts();
        return ResponseEntity.ok(mySubTimelines);
    }

    // 전체 서브타임라인 조회 (비공개 제외, 시작 날짜 순서대로 정렬)
    @GetMapping("/main/{mainTimelineId}")
    public ResponseEntity<SubReadResponseDTO> getAllSubTimelinesByMainTimelineId(@PathVariable Long mainTimelineId) {
        SubReadResponseDTO response = subTimelineService.getAllSubTimelinesWithCommentAndReplyCounts(mainTimelineId); // 서비스 호출 수정
        return ResponseEntity.ok(response);
    }

    // 서브타임라인 진행중 여부 업데이트(완료 또는 진행중)
    @PutMapping("/{subTimelineId}/toggle-done")
    public ResponseEntity<SubUpdateStatusResponseDTO> toggleSubTimelineDoneStatus(@PathVariable Long subTimelineId) {
        SubUpdateStatusResponseDTO response = subTimelineService.toggleSubTimelineDoneStatus(subTimelineId);
        return ResponseEntity.ok(response);
    }
}

