package com.api.RecordTimeline.domain.comment.domain;

import com.api.RecordTimeline.domain.base.BaseEntity;
import com.api.RecordTimeline.domain.member.domain.Member;
import com.api.RecordTimeline.domain.reply.domain.Reply;
import com.api.RecordTimeline.domain.subTimeline.domain.SubTimeline;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Entity
@Table(name = "comment")
public class Comment extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sub_timeline_id", nullable = false)
    private SubTimeline subTimeline;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @Column(nullable = false)
    private String content;

    @OneToMany(mappedBy = "comment", cascade = CascadeType.ALL, orphanRemoval = false)
    private List<Reply> replies = new ArrayList<>();  // 대댓글

    @Column(nullable = false)
    private boolean isDeleted = false; // 삭제 플래그 추가

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
