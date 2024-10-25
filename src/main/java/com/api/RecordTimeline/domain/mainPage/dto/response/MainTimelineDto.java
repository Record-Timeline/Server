package com.api.RecordTimeline.domain.mainPage.dto.response;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;


@AllArgsConstructor
@NoArgsConstructor
@Getter
public class MainTimelineDto {
    private Long id;
    private String title;
    private LocalDate startDate;
    private LocalDate endDate;
    private boolean isPrivate;
    private boolean isDone;

}