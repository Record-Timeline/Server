package com.api.RecordTimeline.domain.subTimeline.controller;

import com.api.RecordTimeline.domain.subTimeline.domain.SubTimeline;
import com.api.RecordTimeline.domain.subTimeline.dto.SubTimelineCreateRequest;
import com.api.RecordTimeline.domain.subTimeline.service.SubTimelineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/sub-timelines")
public class SubTimelineController {
    @Autowired
    private SubTimelineService subTimelineService;

    @PostMapping(consumes = "multipart/form-data")
    public ResponseEntity<SubTimeline> createSubTimeline(@ModelAttribute SubTimelineCreateRequest request) {
        SubTimeline created = subTimelineService.createSubTimeline(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @GetMapping("/main/{mainTimelineId}")
    public ResponseEntity<List<SubTimeline>> getSubTimelinesByMainTimelineId(@PathVariable Long mainTimelineId) {
        List<SubTimeline> subTimelines = subTimelineService.getSubTimelinesByMainTimelineId(mainTimelineId);
        return ResponseEntity.ok(subTimelines);
    }

    @PutMapping("/{subTimelineId}")
    public ResponseEntity<SubTimeline> updateSubTimeline(@PathVariable Long subTimelineId, @RequestBody SubTimelineCreateRequest request) {
        SubTimeline updated = subTimelineService.updateSubTimeline(subTimelineId, request);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{subTimelineId}")
    public ResponseEntity<?> deleteSubTimeline(@PathVariable Long subTimelineId) {
        subTimelineService.deleteSubTimeline(subTimelineId);
        return ResponseEntity.noContent().build();  // 204 No Content
    }
}

