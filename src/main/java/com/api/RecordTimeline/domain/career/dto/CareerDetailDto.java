package com.api.RecordTimeline.domain.career.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CareerDetailDto {
    private Long id;

    private List<CareerDto> careers;
    private List<CertificateDto> certificates;
    private List<EducationDto> educations;
    private List<ForeignLanguageDto> languages;

    public CareerDetailDto(Long id) {
        this.id = id;
    }
}
