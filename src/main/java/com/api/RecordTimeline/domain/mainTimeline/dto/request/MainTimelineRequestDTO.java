package com.api.RecordTimeline.domain.mainTimeline.dto.request;

import com.api.RecordTimeline.domain.mainTimeline.domain.MainTimeline;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
public class MainTimelineRequestDTO {
    private String title;
    private LocalDate startDate;
    private LocalDate endDate;

    // DTO로부터 엔티티 객체를 생성하는 메서드
    public MainTimeline toEntity() {
        return MainTimeline.builder()
                .title(this.title)
                .startDate(this.startDate)
                .endDate(this.endDate)
                .build();
    }
}
