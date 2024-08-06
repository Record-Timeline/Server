package com.api.RecordTimeline.domain.career.controller;

import com.api.RecordTimeline.domain.career.domain.*;
import com.api.RecordTimeline.domain.career.service.CareerDetailService;
import com.api.RecordTimeline.global.success.SuccessResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/career-details")
@RequiredArgsConstructor
public class CareerDetailController {

    private final CareerDetailService careerDetailService;

    @PostMapping
    public SuccessResponse<CareerDetail> addCareerDetail(@RequestBody CareerDetail careerDetail) {
        CareerDetail savedCareerDetail = careerDetailService.saveCareerDetail(careerDetail);
        return new SuccessResponse<>(savedCareerDetail);
    }

    @DeleteMapping("/{id}")
    public SuccessResponse<Long> deleteCareerDetail(@PathVariable Long id) {
        careerDetailService.deleteCareerDetail(id);
        return new SuccessResponse<>(id);
    }

    @PostMapping("/{id}/careers")
    public SuccessResponse<Career> addCareer(@PathVariable Long id, @RequestBody Career career) {
        Career savedCareer = careerDetailService.addCareer(id, career);
        return new SuccessResponse<>(savedCareer);
    }


    @PostMapping("/{id}/educations")
    public SuccessResponse<Education> addEducation(@PathVariable Long id, @RequestBody Education education) {
        Education savedEducation = careerDetailService.addEducation(id, education);
        return new SuccessResponse<>(savedEducation);
    }


    @PutMapping("/careers/{id}")
    public SuccessResponse<Career> updateCareer(@PathVariable Long id, @RequestBody Career career) {
        Career updatedCareer = careerDetailService.updateCareer(id, career);
        return new SuccessResponse<>(updatedCareer);
    }


    @PutMapping("/educations/{id}")
    public SuccessResponse<Education> updateEducation(@PathVariable Long id, @RequestBody Education education) {
        Education updatedEducation = careerDetailService.updateEducation(id, education);
        return new SuccessResponse<>(updatedEducation);
    }
}
