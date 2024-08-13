package com.api.RecordTimeline.domain.subTimeline.dto.response;

import com.api.RecordTimeline.domain.subTimeline.domain.SubTimeline;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@AllArgsConstructor
public class SubMyTimelineResponseDTO {

    private Long id;
    private String title;
    private String content;
    private LocalDate startDate;
    private LocalDate endDate;
    private int likeCount;
    private int bookmarkCount;
    private boolean isPrivate;

    // 서브타임라인에서 DTO 생성하는 메서드
    public static SubMyTimelineResponseDTO from(SubTimeline subTimeline) {
        return new SubMyTimelineResponseDTO(
                subTimeline.getId(),
                subTimeline.getTitle(),
                subTimeline.getContent(),
                subTimeline.getStartDate(),
                subTimeline.getEndDate(),
                subTimeline.getLikeCount(),
                subTimeline.getBookmarkCount(),
                subTimeline.isPrivate()
        );
    }
}
