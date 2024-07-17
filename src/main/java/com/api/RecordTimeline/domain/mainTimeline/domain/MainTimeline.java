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
    private Long id; // 메인 타임라인 ID

    @Column(nullable = false)
    private String title; // 메인 타임라인 제목

    @Column(nullable = false)
    private LocalDate startDate; // 시작 날짜

    @Column
    private LocalDate endDate; // 종료 날짜

    @Setter
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member; // 해당 메인 타임라인의 멤버

    @OneToMany(mappedBy = "mainTimeline", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<SubTimeline> subTimelines = new ArrayList<>(); // 메인 타임라인에 속한 서브 타임라인

    @Builder
    public MainTimeline(Long id, String title, LocalDate startDate, LocalDate endDate, Member member) {
        this.id = id;
        this.title = title;
        this.startDate = startDate;
        this.endDate = endDate;
//        this.member = member;
        setMember(member); // Member를 설정할 때 검증 로직 포함
    }

    public void setMember(Member member) {
        if (member == null) {
            throw new IllegalArgumentException("Member cannot be null");
        }
        this.member = member;
    }
}
