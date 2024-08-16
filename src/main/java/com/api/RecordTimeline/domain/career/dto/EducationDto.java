package com.api.RecordTimeline.domain.career.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class EducationDto {
    private Long id;
    private String degree;
    private String institution;
    private String major;
    private LocalDate startDate;
    private LocalDate endDate;
    private String userEmail;
}
