package com.api.RecordTimeline.domain.mainTimeline.dto.request;

import com.api.RecordTimeline.domain.mainTimeline.domain.MainTimeline;
import lombok.*;

import java.time.LocalDate;

@Getter
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class UpdateMainTimelineRequestDTO {

    private String title;
    private LocalDate startDate;
    private LocalDate endDate;

    public MainTimeline toEntity() {
        return MainTimeline.builder()
                .title(this.title)
                .startDate(this.startDate)
                .endDate(this.endDate)
                .build();
    }
}

