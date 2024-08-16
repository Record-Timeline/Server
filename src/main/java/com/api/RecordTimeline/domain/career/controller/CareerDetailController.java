package com.api.RecordTimeline.domain.career.controller;

import com.api.RecordTimeline.domain.career.domain.*;
import com.api.RecordTimeline.domain.career.dto.*;
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
    public SuccessResponse<CareerDetailDto> addCareerDetail(@RequestBody CareerDetail careerDetail) {
        CareerDetail savedCareerDetail = careerDetailService.saveCareerDetail(careerDetail);

        CareerDetailDto careerDetailDto = new CareerDetailDto(
                savedCareerDetail.getId(),
                savedCareerDetail.getCareers().stream()
                        .map(career -> new CareerDto(career.getId(), career.getCompanyName(), career.getDuty(), career.getPosition(), career.getStartDate(), career.getEndDate(), career.getUserEmail()))
                        .toList(),
                savedCareerDetail.getCertificates().stream()
                        .map(certificate -> new CertificateDto(certificate.getId(), certificate.getName(), certificate.getDate(), certificate.getUserEmail()))
                        .toList(),
                savedCareerDetail.getEducations().stream()
                        .map(education -> new EducationDto(education.getId(), education.getDegree(),education.getInstitution(), education.getStartDate(), education.getEndDate(), education.getUserEmail()))
                        .toList(),
                savedCareerDetail.getLanguages().stream()
                        .map(language -> new ForeignLanguageDto(language.getId(), language.getLanguageName(), language.getProficiency().name(), language.getUserEmail()))
                        .toList()
        );

        return new SuccessResponse<>(careerDetailDto);
    }

    @DeleteMapping("/{id}")
    public SuccessResponse<Long> deleteCareerDetail(@PathVariable Long id) {
        careerDetailService.deleteCareerDetail(id);
        return new SuccessResponse<>(id);
    }

    @GetMapping("/{memberId}")
    public SuccessResponse<CareerDetailDto> getCareerDetailByMemberId(@PathVariable Long memberId) {
        CareerDetail careerDetail = careerDetailService.getCareerDetailByMemberId(memberId);

        CareerDetailDto careerDetailDto = new CareerDetailDto(
                careerDetail.getId(),
                careerDetail.getCareers().stream()
                        .map(career -> new CareerDto(career.getId(), career.getCompanyName(), career.getDuty(), career.getPosition(), career.getStartDate(), career.getEndDate(), career.getUserEmail()))
                        .toList(),
                careerDetail.getCertificates().stream()
                        .map(certificate -> new CertificateDto(certificate.getId(), certificate.getName(), certificate.getDate(), certificate.getUserEmail()))
                        .toList(),
                careerDetail.getEducations().stream()
                        .map(education -> new EducationDto(education.getId(), education.getDegree(),education.getInstitution(), education.getStartDate(), education.getEndDate(), education.getUserEmail()))
                        .toList(),
                careerDetail.getLanguages().stream()
                        .map(language -> new ForeignLanguageDto(language.getId(), language.getLanguageName(), language.getProficiency().name(), language.getUserEmail()))
                        .toList()
        );

        return new SuccessResponse<>(careerDetailDto);
    }
}
