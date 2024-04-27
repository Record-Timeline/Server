package com.api.RecordTimeline.domain.mainTimeline.dto;

import com.api.RecordTimeline.domain.mainTimeline.domain.MainTimeline;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor // 기본 생성자를 만들어줍니다.
public class MainTimelineRequestDTO {
    private String title;
    private String description;
    private LocalDate startDate;
    private LocalDate endDate;
    // 기타 필요한 필드들을 여기에 추가합니다.

    // DTO로부터 엔티티 객체를 생성하는 메서드
    public MainTimeline toEntity() {
        return MainTimeline.builder()
                .title(this.title)
                .description(this.description)
                .startDate(this.startDate)
                .endDate(this.endDate)
                .build();
    }
}
