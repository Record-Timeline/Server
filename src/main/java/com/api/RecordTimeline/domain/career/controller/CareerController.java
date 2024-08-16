package com.api.RecordTimeline.domain.career.controller;

import com.api.RecordTimeline.domain.career.domain.Career;
import com.api.RecordTimeline.domain.career.dto.CareerDto;
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
    public ResponseEntity<SuccessResponse<CareerDto>> addCareer(@RequestBody Career career) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        career = career.toBuilder().userEmail(email).build();
        Career savedCareer = careerService.addCareer(career);
        CareerDto careerDto = new CareerDto(savedCareer.getId(), savedCareer.getCompanyName(), savedCareer.getDuty(), savedCareer.getPosition(), savedCareer.getStartDate(), savedCareer.getEndDate(), savedCareer.getUserEmail());
        return ResponseEntity.ok(new SuccessResponse<>(careerDto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<SuccessResponse<CareerDto>> updateCareer(@PathVariable Long id, @RequestBody Career career) {
        Career updatedCareer = careerService.updateCareer(id, career);
        CareerDto careerDto = new CareerDto(updatedCareer.getId(), updatedCareer.getCompanyName(), updatedCareer.getDuty(), updatedCareer.getPosition(), updatedCareer.getStartDate(), updatedCareer.getEndDate(), updatedCareer.getUserEmail());
        return ResponseEntity.ok(new SuccessResponse<>(careerDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<SuccessResponse<Long>> deleteCareer(@PathVariable Long id) {
        careerService.deleteCareer(id);
        return ResponseEntity.ok(new SuccessResponse<>(id));
    }
}