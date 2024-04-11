package com.api.RecordTimeline.domain.mainTimeline.domain;

import com.api.RecordTimeline.domain.base.BaseEntity;
import com.api.RecordTimeline.domain.member.domain.Member;
import com.api.RecordTimeline.domain.subTimeline.domain.SubTimeline;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.ArrayList;
import java.util.List;

/** 연관관계를 매핑을 위해 작성. 완성된 코드 X **/
@Entity
@Getter
@SuperBuilder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
/*@AllArgsConstructor(access = AccessLevel.PRIVATE)*/
@Table(name = "main_timeline") // DB 테이블 이름 명시
public class MainTimeline extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "main_timeline_id") // 컬럼 이름 명시
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "member_id", nullable = false) // 연관 관계의 주인을 명시
    private Member member;

    //@OneToMany 관계에서 cascade = CascadeType.ALL은 연관된 SubTimeline 엔티티가 MainTimeline 엔티티의 생명주기를 따르도록 하고
    // orphanRemoval = true는 MainTimeline과의 연관이 끊긴 SubTimeline 엔티티를 DB에서 자동으로 삭제
    @OneToMany(mappedBy = "mainTimeline", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<SubTimeline> subTimelines = new ArrayList<>();


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
