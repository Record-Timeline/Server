package com.api.RecordTimeline.domain.mainTimeline.controller;

import com.api.RecordTimeline.domain.mainTimeline.domain.MainTimeline;
import com.api.RecordTimeline.domain.mainTimeline.service.MainTimelineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/main-timelines")
public class MainTimelineController {

    private final MainTimelineService mainTimelineService;

    @Autowired
    public MainTimelineController(MainTimelineService mainTimelineService) {
        this.mainTimelineService = mainTimelineService;
    }

    @PostMapping
    public ResponseEntity<MainTimeline> createMainTimeline(@RequestBody MainTimeline mainTimeline) {
        MainTimeline created = mainTimelineService.createMainTimeline(mainTimeline);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
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
