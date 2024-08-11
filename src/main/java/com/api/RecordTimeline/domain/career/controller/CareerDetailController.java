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

    @GetMapping("/{memberId}")
    public SuccessResponse<CareerDetail> getCareerDetailByMemberId(@PathVariable Long memberId) {
        CareerDetail careerDetail = careerDetailService.getCareerDetailByMemberId(memberId);
        return new SuccessResponse<>(careerDetail);
    }
}
