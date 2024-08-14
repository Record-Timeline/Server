package com.api.RecordTimeline.domain.mainTimeline.controller;

import com.api.RecordTimeline.domain.mainTimeline.domain.MainTimeline;
import com.api.RecordTimeline.domain.mainTimeline.dto.request.MainTimelineRequestDTO;
import com.api.RecordTimeline.domain.mainTimeline.dto.request.UpdateMainTimelineRequestDTO;
import com.api.RecordTimeline.domain.mainTimeline.dto.response.*;
import com.api.RecordTimeline.domain.mainTimeline.service.MainTimelineService;
import com.api.RecordTimeline.global.exception.ApiException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/main-timelines")
@RequiredArgsConstructor
public class MainTimelineController {

    private final MainTimelineService mainTimelineService;

    @PostMapping
    public ResponseEntity<CreateResponseDTO> createMainTimeline(@RequestBody MainTimelineRequestDTO mainTimelineRequestDTO) {
        try {
            MainTimeline created = mainTimelineService.createMainTimeline(mainTimelineRequestDTO);
            return ResponseEntity.ok(CreateResponseDTO.success(created.getId()));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(CreateResponseDTO.failure(e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(CreateResponseDTO.failure("Unexpected error occurred: " + e.getMessage()));
        }
    }



    @GetMapping("/my")
    public ResponseEntity<List<ReadResponseDTO.TimelineDetails>> getMyTimelines() {
        List<MainTimeline> timelines = mainTimelineService.getMyTimelines();
        if (timelines.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        List<ReadResponseDTO.TimelineDetails> details = timelines.stream()
                .map(timeline -> new ReadResponseDTO.TimelineDetails(timeline.getId(), timeline.getTitle(), timeline.getStartDate(), timeline.getEndDate(), timeline.isDone()))
                .collect(Collectors.toList());
        return ResponseEntity.ok(details);
    }

    // 멤버 ID로 모든 메인 타임라인 조회 수정본
    @GetMapping("/member/{memberId}")
    public ResponseEntity<List<ReadResponseDTO.TimelineDetails>> getTimelinesByMemberId(@PathVariable Long memberId) {
        List<MainTimeline> timelines = mainTimelineService.getTimelinesByMemberId(memberId);
        if (timelines.isEmpty()) {
            return ResponseEntity.noContent().build();  // 내용이 없을 경우 No Content 상태 반환
        }
        List<ReadResponseDTO.TimelineDetails> details = timelines.stream()
                .map(timeline -> new ReadResponseDTO.TimelineDetails(timeline.getId(), timeline.getTitle(), timeline.getStartDate(), timeline.getEndDate(), timeline.isDone()))
                .collect(Collectors.toList());
        return ResponseEntity.ok(details);
    }


    @GetMapping("/{id}") //메인타임라인 ID
    public ResponseEntity<ReadResponseDTO> getMainTimelineById(@PathVariable Long id) {
        try {
            MainTimeline timeline = mainTimelineService.getMainTimelineById(id);
            ReadResponseDTO response = new ReadResponseDTO(List.of(new ReadResponseDTO.TimelineDetails(
                    timeline.getId(), timeline.getTitle(), timeline.getStartDate(), timeline.getEndDate(), timeline.isDone()
            )));
            return ResponseEntity.ok(response);
        } catch (NoSuchElementException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "메인 타임라인을 찾을 수 없습니다.", e);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<UpdateResponseDTO> updateMainTimeline(@PathVariable Long id, @RequestBody UpdateMainTimelineRequestDTO requestDTO) {
        try {
            MainTimeline updatedTimeline = mainTimelineService.updateMainTimeline(id, requestDTO);
            return ResponseEntity.ok(new UpdateResponseDTO("SU", "업데이트에 성공했습니다."));
        } catch (ApiException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new UpdateResponseDTO("AD", e.getMessage()));
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new UpdateResponseDTO("NF", "해당 타임라인을 찾을 수 없습니다."));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new UpdateResponseDTO("UE", "업데이트에 실패했습니다."));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<DeleteResponseDTO> deleteMainTimeline(@PathVariable Long id) {
        try {
            mainTimelineService.deleteMainTimeline(id);
            return ResponseEntity.ok(new DeleteResponseDTO("SU", "삭제에 성공했습니다."));
        } catch (ApiException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new DeleteResponseDTO("AD", e.getMessage()));
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new DeleteResponseDTO("NF", "해당 타임라인을 찾을 수 없습니다."));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new DeleteResponseDTO("UE", "삭제에 실패했습니다."));
        }
    }

    @PutMapping("/{mainTimelineId}/privacy")
    public ResponseEntity<PrivacyUpdateResponseDTO> setMainTimelinePrivacy(@PathVariable Long mainTimelineId,
                                                                           @RequestParam boolean isPrivate) {
        mainTimelineService.setMainTimelinePrivacy(mainTimelineId, isPrivate);

        String message = isPrivate ? "메인타임라인이 비공개 처리 되었습니다." : "메인타임라인이 공개 처리 되었습니다.";
        return ResponseEntity.ok(PrivacyUpdateResponseDTO.success(message));
    }

    @PutMapping("/{mainTimelineId}/status")
    public ResponseEntity<UpdateStatusResponseDTO> updateMainTimelineStatus(@PathVariable Long mainTimelineId, @RequestParam boolean isDone) {
        try {
            mainTimelineService.updateMainTimelineStatus(mainTimelineId, isDone);
            String message = isDone ? "메인타임라인이 완료 상태로 업데이트 되었습니다." : "메인타임라인이 진행중 상태로 업데이트 되었습니다.";
            return ResponseEntity.ok(UpdateStatusResponseDTO.success(message));
        } catch (NoSuchElementException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "메인 타임라인을 찾을 수 없습니다.", e);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(UpdateStatusResponseDTO.failure("상태 업데이트에 실패했습니다."));
        }
    }

}
