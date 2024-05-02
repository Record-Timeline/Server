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
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "main_timeline") // DB 테이블 이름 명시
public class MainTimeline extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String description;

    @Column(nullable = false)
    private LocalDate startDate; // 시작 날짜

    @Column(nullable = false)
    private LocalDate endDate; // 종료 날짜

    @Setter
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "member_id", nullable = false) // 연관 관계의 주인을 명시
    private Member member;

    @OneToMany(mappedBy = "mainTimeline", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<SubTimeline> subTimelines = new ArrayList<>();

    // Lombok의 @Builder 어노테이션으로 인해 자동으로 생성자가 생성됩니다.
    // 추가적인 로직이 필요하다면 아래와 같이 생성자를 직접 선언할 수 있습니다.
    @Builder
    public MainTimeline(String title, String description, LocalDate startDate, LocalDate endDate, Member member) {
        this.title = title;
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
        this.member = member;
        // 서브타임라인 리스트 초기화는 필드 선언 시점에서 이루어집니다.
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
