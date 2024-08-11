package com.api.RecordTimeline.domain.career.controller;

import com.api.RecordTimeline.domain.career.domain.Career;
import com.api.RecordTimeline.domain.career.service.CareerService;
import com.api.RecordTimeline.global.success.SuccessResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/careers")
@RequiredArgsConstructor
public class CareerController {

    private final CareerService careerService;

    @PostMapping
    public ResponseEntity<SuccessResponse<Career>> addCareer(@RequestBody Career career) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        career = career.toBuilder().userEmail(email).build();
        Career savedCareer = careerService.addCareer(career);
        return ResponseEntity.ok(new SuccessResponse<>(savedCareer));
    }

    @PutMapping("/{id}")
    public ResponseEntity<SuccessResponse<Career>> updateCareer(@PathVariable Long id, @RequestBody Career career) {
        Career updatedCareer = careerService.updateCareer(id, career);
        return ResponseEntity.ok(new SuccessResponse<>(updatedCareer));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<SuccessResponse<Long>> deleteCareer(@PathVariable Long id) {
        careerService.deleteCareer(id);
        return ResponseEntity.ok(new SuccessResponse<>(id));
    }
}
