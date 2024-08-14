package com.api.RecordTimeline.domain.subTimeline.controller;

import com.api.RecordTimeline.domain.subTimeline.domain.SubTimeline;
import com.api.RecordTimeline.domain.subTimeline.dto.request.SubTimelineCreateRequest;
import com.api.RecordTimeline.domain.subTimeline.dto.response.*;
import com.api.RecordTimeline.domain.subTimeline.service.SubTimelineService;
import com.api.RecordTimeline.global.exception.ApiException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
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
    public ResponseEntity<SubCreateResponseDTO> createSubTimeline(@RequestBody SubTimelineCreateRequest request) {
        try {
            SubTimeline created = subTimelineService.createSubTimeline(request);
            return ResponseEntity.status(HttpStatus.CREATED).body(SubCreateResponseDTO.success(created.getId()));
        } catch (ApiException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(SubCreateResponseDTO.failure("Creation/Update Failed", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(SubCreateResponseDTO.failure("Creation/Update Failed", e.getMessage()));
        }
    }

    @GetMapping("/main/{mainTimelineId}/ordered")
    public ResponseEntity<SubReadResponseDTO> getSubTimelinesByMainTimelineIdOrderByStartDate(@PathVariable Long mainTimelineId) {
        try {
            List<SubTimeline> subTimelines = subTimelineService.getSubTimelinesByMainTimelineIdOrderByStartDate(mainTimelineId);
            String mainTimelineTitle = subTimelineService.getMainTimelineTitle(mainTimelineId); // 메인타임라인 제목 가져오기
            return ResponseEntity.ok(SubReadResponseDTO.from(subTimelines, mainTimelineTitle));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PutMapping("/{subTimelineId}")
    public ResponseEntity<SubUpdateResponseDTO> updateSubTimeline(@PathVariable Long subTimelineId, @RequestBody SubTimelineCreateRequest request) {
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
        List<SubMyTimelineResponseDTO> mySubTimelines = subTimelineService.getMySubTimelines();
        return ResponseEntity.ok(mySubTimelines);
    }

    // 전체 서브타임라인 조회 (비공개 제외, 시작 날짜 순서대로 정렬)
    @GetMapping("/main/{mainTimelineId}")
    public ResponseEntity<SubReadResponseDTO> getAllSubTimelinesByMainTimelineId(@PathVariable Long mainTimelineId) {
        List<SubTimeline> subTimelines = subTimelineService.getAllSubTimelinesByMainTimelineId(mainTimelineId);
        String mainTimelineTitle = subTimelineService.getMainTimelineTitle(mainTimelineId);
        return ResponseEntity.ok(SubReadResponseDTO.from(subTimelines, mainTimelineTitle));
    }

    // 서브타임라인 진행중 여부 업데이트(완료 또는 진행중)
    @PutMapping("/{subTimelineId}/done-status")
    public ResponseEntity<SubUpdateStatusResponseDTO> updateSubTimelineStatus(@PathVariable Long subTimelineId, @RequestParam boolean isDone) {
        SubUpdateStatusResponseDTO response = subTimelineService.updateSubTimelineStatus(subTimelineId, isDone);
        return ResponseEntity.ok(response);
    }
}

