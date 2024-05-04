package com.api.RecordTimeline.domain.mainTimeline.controller;

import com.api.RecordTimeline.domain.mainTimeline.domain.MainTimeline;
import com.api.RecordTimeline.domain.mainTimeline.dto.request.MainTimelineRequestDTO;
import com.api.RecordTimeline.domain.mainTimeline.dto.response.CreateResponseDTO;
import com.api.RecordTimeline.domain.mainTimeline.dto.response.DeleteResponseDTO;
import com.api.RecordTimeline.domain.mainTimeline.dto.response.ReadResponseDTO;
import com.api.RecordTimeline.domain.mainTimeline.dto.response.UpdateResponseDTO;
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
    public ResponseEntity<CreateResponseDTO> createMainTimeline(@RequestBody MainTimelineRequestDTO mainTimelineRequestDTO) {
        try {
            MainTimeline mainTimeline = mainTimelineRequestDTO.toEntity();
            MainTimeline created = mainTimelineService.createMainTimeline(mainTimeline);
            return ResponseEntity.ok(CreateResponseDTO.success(created.getId()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(CreateResponseDTO.failure());
        }
    }


    // 멤버 ID로 모든 메인 타임라인 조회
    @GetMapping("/member/{memberId}")
    public ResponseEntity<ReadResponseDTO> getTimelinesByMemberId(@PathVariable Long memberId) {
        List<MainTimeline> timelines = mainTimelineService.getTimelinesByMemberId(memberId);
        if (timelines.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(ReadResponseDTO.from(timelines));
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
    public ResponseEntity<UpdateResponseDTO> updateMainTimeline(@PathVariable Long id, @RequestBody MainTimeline mainTimeline) {
        try {
            MainTimeline updated = mainTimelineService.updateMainTimeline(id, mainTimeline);
            return ResponseEntity.ok(new UpdateResponseDTO("SU", "Success"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new UpdateResponseDTO("UF", "업데이트에 실패했습니다."));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<DeleteResponseDTO> deleteMainTimeline(@PathVariable Long id) {
        try {
            mainTimelineService.deleteMainTimeline(id);
            return ResponseEntity.ok(new DeleteResponseDTO("SU", "Success"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new DeleteResponseDTO("UF", "삭제에 실패했습니다."));
        }
    }
}

