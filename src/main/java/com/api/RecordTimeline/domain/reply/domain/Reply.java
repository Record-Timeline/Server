package com.api.RecordTimeline.domain.reply.domain;

import com.api.RecordTimeline.domain.base.BaseEntity;
import com.api.RecordTimeline.domain.comment.domain.Comment;
import com.api.RecordTimeline.domain.member.domain.Member;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "reply")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder(toBuilder = true)
public class Reply extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "comment_id", nullable = true)
    private Comment comment;  // Comment와 연관 관계 설정

//    public static class ReplyBuilder {
//        private Comment comment;
//
//        public ReplyBuilder comment(Comment comment) {
//            this.comment = comment;
//            return this;
//        }
//    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;  // 작성자와의 연관 관계

    @Column(nullable = false)
    private String content;  // 대댓글 내용

    @Column(nullable = false)
    private boolean isDeleted = false;

    // likeCount 필드를 추가
    @Column(nullable = false)
    private int likeCount = 0;

    // 좋아요 수를 조정하는 메서드
    public void adjustLikeCount(int adjustment) {
        this.likeCount += adjustment;
        if (this.likeCount < 0) {
            this.likeCount = 0;
        }
    }

    public int getLikeCount() {
        return this.likeCount;
    }
}