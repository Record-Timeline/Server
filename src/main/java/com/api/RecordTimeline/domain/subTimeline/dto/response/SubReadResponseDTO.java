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
    private String mainTimelineTitle;

    @Getter
    @AllArgsConstructor
    public static class SubTimelineDetails {
        private Long id;
        private String title;
        private String content;
        private LocalDate startDate;
        private LocalDate endDate;
        private int likeCount;
        private int bookmarkCount;
        private boolean isPrivate;
    }

    public static SubReadResponseDTO from(List<SubTimeline> subTimelines, String mainTimelineTitle) {
        List<SubTimelineDetails> details = subTimelines.stream()
                .map(subTimeline -> new SubTimelineDetails(
                        subTimeline.getId(),
                        subTimeline.getTitle(),
                        subTimeline.getContent(),
                        subTimeline.getStartDate(),
                        subTimeline.getEndDate(),
                        subTimeline.getLikeCount(),
                        subTimeline.getBookmarkCount(),
                        subTimeline.isPrivate()
                )).collect(Collectors.toList());
        return new SubReadResponseDTO(details, mainTimelineTitle);
    }

    // 모든 서브타임라인을 가져오는 경우 호출 (비공개 제외)
    public static SubReadResponseDTO from(List<SubTimeline> subTimelines) {
        if (subTimelines.isEmpty()) {
            return new SubReadResponseDTO(List.of(), null);
        }

        // 모든 서브타임라인은 같은 메인 타임라인에 속해 있으므로 첫 번째 타임라인의 메인 타임라인 제목을 가져옴
        String mainTimelineTitle = subTimelines.get(0).getMainTimeline().getTitle();

        List<SubTimelineDetails> details = subTimelines.stream()
                .map(subTimeline -> new SubTimelineDetails(
                        subTimeline.getId(),
                        subTimeline.getTitle(),
                        subTimeline.getContent(),
                        subTimeline.getStartDate(),
                        subTimeline.getEndDate(),
                        subTimeline.getLikeCount(),
                        subTimeline.getBookmarkCount(),
                        subTimeline.isPrivate()
                )).collect(Collectors.toList());
        return new SubReadResponseDTO(details, mainTimelineTitle);
    }
}
