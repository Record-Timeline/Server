package com.api.RecordTimeline.domain.mainTimeline.controller;

import com.api.RecordTimeline.domain.mainTimeline.domain.MainTimeline;
import com.api.RecordTimeline.domain.mainTimeline.dto.request.MainTimelineRequestDTO;
import com.api.RecordTimeline.domain.mainTimeline.dto.request.UpdateMainTimelineRequestDTO;
import com.api.RecordTimeline.domain.mainTimeline.dto.response.CreateResponseDTO;
import com.api.RecordTimeline.domain.mainTimeline.dto.response.DeleteResponseDTO;
import com.api.RecordTimeline.domain.mainTimeline.dto.response.ReadResponseDTO;
import com.api.RecordTimeline.domain.mainTimeline.dto.response.UpdateResponseDTO;
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
            MainTimeline mainTimeline = mainTimelineRequestDTO.toEntity();
            MainTimeline created = mainTimelineService.createMainTimeline(mainTimeline);
            return ResponseEntity.ok(CreateResponseDTO.success(created.getId()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(CreateResponseDTO.failure());
        }
    }

    // 멤버 ID로 모든 메인 타임라인 조회 수정본
    @GetMapping("/member/{memberId}")
    public ResponseEntity<List<ReadResponseDTO.TimelineDetails>> getTimelinesByMemberId(@PathVariable Long memberId) {
        List<MainTimeline> timelines = mainTimelineService.getTimelinesByMemberId(memberId);
        if (timelines.isEmpty()) {
            return ResponseEntity.noContent().build();  // 내용이 없을 경우 No Content 상태 반환
        }
        List<ReadResponseDTO.TimelineDetails> details = timelines.stream()
                .map(timeline -> new ReadResponseDTO.TimelineDetails(timeline.getId(), timeline.getTitle(), timeline.getStartDate(), timeline.getEndDate()))
                .collect(Collectors.toList());
        return ResponseEntity.ok(details);
    }


    @GetMapping("/{id}") //메인타임라인 ID
    public ResponseEntity<ReadResponseDTO> getMainTimelineById(@PathVariable Long id) {
        try {
            MainTimeline timeline = mainTimelineService.getMainTimelineById(id);
            ReadResponseDTO response = new ReadResponseDTO(List.of(new ReadResponseDTO.TimelineDetails(
                    timeline.getId(), timeline.getTitle(), timeline.getStartDate(), timeline.getEndDate()
            )));
            return ResponseEntity.ok(response);
        } catch (Exception e) {
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
}
