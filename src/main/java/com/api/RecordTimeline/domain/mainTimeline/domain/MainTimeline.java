package com.api.RecordTimeline.domain.mainTimeline.domain;

import com.api.RecordTimeline.domain.base.BaseEntity;
import com.api.RecordTimeline.domain.member.domain.Member;
import com.api.RecordTimeline.domain.subTimeline.domain.SubTimeline;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Table(name = "main_timeline")
public class MainTimeline extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private LocalDate startDate;

    @Column
    private LocalDate endDate;

    @Column(nullable = false, columnDefinition = "TINYINT(1)")
    private boolean isPrivate; // 공개 여부를 저장하는 필드 추가

    @Column(nullable = false, columnDefinition = "TINYINT(1)")
    private boolean isDone; // 진행 상태를 저장하는 필드 추가

    @Setter
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @OneToMany(mappedBy = "mainTimeline", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<SubTimeline> subTimelines = new ArrayList<>();

    @Builder
    public MainTimeline(Long id, String title, LocalDate startDate, LocalDate endDate, Member member, boolean isPrivate, boolean isDone) {
        this.id = id;
        this.title = title;
        this.startDate = startDate;
        this.endDate = endDate;
        this.isPrivate = isPrivate;
        this.isDone = isDone;
        setMember(member);
    }

    public void setMember(Member member) {
        if (member == null) {
            throw new IllegalArgumentException("Member cannot be null");
        }
        this.member = member;
    }
}
