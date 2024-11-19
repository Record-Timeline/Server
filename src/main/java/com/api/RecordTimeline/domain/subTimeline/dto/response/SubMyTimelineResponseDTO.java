package com.api.RecordTimeline.domain.subTimeline.dto.response;

import com.api.RecordTimeline.domain.comment.repository.CommentRepository;
import com.api.RecordTimeline.domain.reply.repository.ReplyRepository;
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
    private boolean isDone;
    private int totalCommentAndReplyCount;

    // 서브타임라인에서 DTO 생성하는 메서드
    public static SubMyTimelineResponseDTO from(SubTimeline subTimeline, int totalCommentAndReplyCount) {
        return new SubMyTimelineResponseDTO(
                subTimeline.getId(),
                subTimeline.getTitle(),
                subTimeline.getContent(),
                subTimeline.getStartDate(),
                subTimeline.getEndDate(),
                subTimeline.getLikeCount(),
                subTimeline.getBookmarkCount(),
                subTimeline.isPrivate(),
                subTimeline.isDone(),
                totalCommentAndReplyCount
        );
    }
}
