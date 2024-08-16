package com.api.RecordTimeline.domain.career.controller;

import com.api.RecordTimeline.domain.career.domain.Education;
import com.api.RecordTimeline.domain.career.dto.EducationDto;
import com.api.RecordTimeline.domain.career.service.EducationService;
import com.api.RecordTimeline.global.success.SuccessResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/educations")
@RequiredArgsConstructor
public class EducationController {

    private final EducationService educationService;

    @PostMapping
    public ResponseEntity<SuccessResponse<EducationDto>> addEducation(@RequestBody Education education) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        education = education.toBuilder().userEmail(email).build();
        Education savedEducation = educationService.addEducation(education);
        EducationDto educationDto = new EducationDto(savedEducation.getId(), savedEducation.getDegree(), savedEducation.getInstitution(), savedEducation.getMajor(), savedEducation.getStartDate(), savedEducation.getEndDate(), savedEducation.getUserEmail());
        return ResponseEntity.ok(new SuccessResponse<>(educationDto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<SuccessResponse<EducationDto>> updateEducation(@PathVariable Long id, @RequestBody Education education) {
        Education updatedEducation = educationService.updateEducation(id, education);
        EducationDto educationDto = new EducationDto(updatedEducation.getId(), updatedEducation.getDegree(), updatedEducation.getInstitution(), updatedEducation.getMajor(), updatedEducation.getStartDate(), updatedEducation.getEndDate(), updatedEducation.getUserEmail());
        return ResponseEntity.ok(new SuccessResponse<>(educationDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<SuccessResponse<Long>> deleteEducation(@PathVariable Long id) {
        educationService.deleteEducation(id);
        return ResponseEntity.ok(new SuccessResponse<>(id));
    }
}