package com.api.RecordTimeline.domain.subTimeline.dto.response;

import com.api.RecordTimeline.domain.comment.repository.CommentRepository;
import com.api.RecordTimeline.domain.reply.repository.ReplyRepository;
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
        private boolean isDone;
        private int totalCommentAndReplyCount;
    }

//    public static SubReadResponseDTO fromWithCommentAndReplyCounts(List<SubTimeline> subTimelines, CommentRepository commentRepository, ReplyRepository replyRepository) {
//        List<SubTimelineDetails> details = subTimelines.stream()
//                .map(subTimeline -> new SubTimelineDetails(
//                        subTimeline.getId(),
//                        subTimeline.getTitle(),
//                        subTimeline.getContent(),
//                        subTimeline.getStartDate(),
//                        subTimeline.getEndDate(),
//                        subTimeline.getLikeCount(),
//                        subTimeline.getBookmarkCount(),
//                        subTimeline.isPrivate(),
//                        subTimeline.isDone(),
//                        totalCount
//                )).collect(Collectors.toList());
//
//        String mainTimelineTitle = subTimelines.isEmpty() ? null : subTimelines.get(0).getMainTimeline().getTitle();
//        return new SubReadResponseDTO(details, mainTimelineTitle);
//    }

    // 모든 서브타임라인을 가져오는 경우 호출 (비공개 제외)
    // 비공개 제외 조회 메서드
    public static SubReadResponseDTO fromWithCommentAndReplyCounts(
            List<SubTimeline> subTimelines,
            String mainTimelineTitle,
            CommentRepository commentRepository,
            ReplyRepository replyRepository
    ) {
        if (subTimelines.isEmpty()) {
            return new SubReadResponseDTO(List.of(), mainTimelineTitle);
        }

        List<SubTimelineDetails> details = subTimelines.stream()
                .map(subTimeline -> {
                    int commentCount = commentRepository.countBySubTimelineId(subTimeline.getId());
                    int replyCount = replyRepository.countByComment_SubTimelineId(subTimeline.getId());
                    int totalCount = commentCount + replyCount; // 댓글 + 대댓글 수 계산

                    return new SubTimelineDetails(
                            subTimeline.getId(),
                            subTimeline.getTitle(),
                            subTimeline.getContent(),
                            subTimeline.getStartDate(),
                            subTimeline.getEndDate(),
                            subTimeline.getLikeCount(),
                            subTimeline.getBookmarkCount(),
                            subTimeline.isPrivate(),
                            subTimeline.isDone(),
                            totalCount // 댓글 + 대댓글 수 추가
                    );
                })
                .collect(Collectors.toList());

        return new SubReadResponseDTO(details, mainTimelineTitle);
    }
}
