package com.api.RecordTimeline.domain.subTimeline.controller;

import com.api.RecordTimeline.domain.subTimeline.domain.SubTimeline;
import com.api.RecordTimeline.domain.subTimeline.dto.request.SubTimelineCreateRequest;
import com.api.RecordTimeline.domain.subTimeline.dto.response.SubCreateResponseDTO;
import com.api.RecordTimeline.domain.subTimeline.dto.response.SubDeleteResponseDTO;
import com.api.RecordTimeline.domain.subTimeline.dto.response.SubReadResponseDTO;
import com.api.RecordTimeline.domain.subTimeline.dto.response.SubUpdateResponseDTO;
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
            return ResponseEntity.ok(SubReadResponseDTO.from(subTimelines));
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
}

