package com.api.RecordTimeline.domain.career.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ForeignLanguageDto {
    private Long id;
    private String languageName;
    private String proficiency;
    private String userEmail;
}
