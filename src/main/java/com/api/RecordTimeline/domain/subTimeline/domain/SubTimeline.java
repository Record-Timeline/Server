package com.api.RecordTimeline.domain.subTimeline.domain;

import com.api.RecordTimeline.domain.base.BaseEntity;
import com.api.RecordTimeline.domain.mainTimeline.domain.MainTimeline;
import com.api.RecordTimeline.domain.member.domain.Member;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "sub_timeline")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(toBuilder = true)
public class SubTimeline extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "main_timeline_id", nullable = false)
    private MainTimeline mainTimeline;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false, columnDefinition = "LONGTEXT")
    private String content;

    @Column(nullable = false)
    private LocalDate startDate;

    @Column
    private LocalDate endDate;

    @Column(nullable = false)
    private int bookmarkCount = 0; // 북마크 수 필드 추가

    // 북마크 수가 음수가 되지 않도록 검증 메서드 추가
    public void adjustBookmarkCount(int adjustment) {
        this.bookmarkCount += adjustment;
        if (this.bookmarkCount < 0) {
            this.bookmarkCount = 0;
        }
    }

    // MainTimeline의 Member를 통해 접근
    public Member getMember() {
        return this.mainTimeline.getMember();
    }
}
