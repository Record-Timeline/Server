package com.api.RecordTimeline.domain.mainTimeline.dto.request;

import com.api.RecordTimeline.domain.mainTimeline.domain.MainTimeline;
import com.api.RecordTimeline.domain.member.domain.Member;
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
    public MainTimeline toEntity(Member member) {
        validate(); // 입력값 검증 추가
        return MainTimeline.builder()
                .title(this.title)
                .startDate(this.startDate)
                .endDate(this.endDate)
                .member(member)
                .build();
    }


    private void validate() {
        if (title == null || title.trim().isEmpty()) {
            throw new IllegalArgumentException("Title cannot be null or empty");
        }
        if (startDate == null) {
            throw new IllegalArgumentException("Start date cannot be null");
        }
        if (endDate != null && endDate.isBefore(startDate)) {
            throw new IllegalArgumentException("End date cannot be before start date");
        }
    }
}
