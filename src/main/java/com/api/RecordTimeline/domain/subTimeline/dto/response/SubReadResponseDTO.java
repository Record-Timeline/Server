package com.api.RecordTimeline.domain.subTimeline.dto.response;

import com.api.RecordTimeline.domain.subTimeline.domain.SubTimeline;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@AllArgsConstructor
public class SubReadResponseDTO {
    private List<SubTimelineDetails> subTimelines;

    @Getter
    @AllArgsConstructor
    public static class SubTimelineDetails {
        private Long id;
        private String title;
        private String content;
        private LocalDate startDate;
        private LocalDate endDate;
    }

    public static SubReadResponseDTO from(List<SubTimeline> subTimelines) {
        List<SubTimelineDetails> details = subTimelines.stream()
                .map(subTimeline -> new SubTimelineDetails(
                        subTimeline.getId(),
                        subTimeline.getTitle(),
                        subTimeline.getContent(),
                        subTimeline.getStartDate(),
                        subTimeline.getEndDate()
                )).collect(Collectors.toList());
        return new SubReadResponseDTO(details);
    }
}
