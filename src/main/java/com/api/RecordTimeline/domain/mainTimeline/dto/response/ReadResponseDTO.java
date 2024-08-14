package com.api.RecordTimeline.domain.mainTimeline.dto.response;

import com.api.RecordTimeline.domain.mainTimeline.domain.MainTimeline;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@AllArgsConstructor
public class ReadResponseDTO {
    private List<TimelineDetails> timelines;

    @Getter
    @AllArgsConstructor
    public static class TimelineDetails {
        private Long id;
        private String title;
        private LocalDate startDate;
        private LocalDate endDate;
        private boolean isDone; // 진행 상태 필드 추가
    }

    public static ReadResponseDTO from(List<MainTimeline> timelines) {
        List<TimelineDetails> details = timelines.stream()
                .map(timeline -> new TimelineDetails(timeline.getId(), timeline.getTitle(), timeline.getStartDate(), timeline.getEndDate(), timeline.isDone()))
                .collect(Collectors.toList());
        return new ReadResponseDTO(details);
    }
}
