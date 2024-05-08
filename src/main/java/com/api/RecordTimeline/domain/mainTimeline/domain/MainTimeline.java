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
@Table(name = "main_timeline") // DB 테이블 이름 명시
public class MainTimeline extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private LocalDate startDate; // 시작 날짜

    @Column // NULL도 가능하게 변경
    private LocalDate endDate; // 종료 날짜

    @Setter
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "member_id", nullable = false) // 연관 관계의 주인을 명시
    private Member member;

    @OneToMany(mappedBy = "mainTimeline", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<SubTimeline> subTimelines = new ArrayList<>();

    @Builder
    public MainTimeline(Long id, String title, LocalDate startDate, LocalDate endDate, Member member) {
        this.title = title;
        this.startDate = startDate;
        this.endDate = endDate;
        this.member = member;

    }

    public void setMember(Member member) {
        if (member == null) {
            throw new IllegalArgumentException("Member cannot be null");
        }
        this.member = member;
    }

    // 서브 타임라인을 추가하는 메서드:

    /*public void addSubTimeline(SubTimeline subTimeline) {
        this.subTimelines.add(subTimeline);
        subTimeline.setMainTimeline(this);
    }

    // 서브 타임라인을 제거하는 메서드:

    public void removeSubTimeline(SubTimeline subTimeline) {
        this.subTimelines.remove(subTimeline);
        subTimeline.setMainTimeline(null);*/
}
