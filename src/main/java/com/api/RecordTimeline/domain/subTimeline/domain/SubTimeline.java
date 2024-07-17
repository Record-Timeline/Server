package com.api.RecordTimeline.domain.subTimeline.domain;

import com.api.RecordTimeline.domain.base.BaseEntity;
import com.api.RecordTimeline.domain.mainTimeline.domain.MainTimeline;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "sub_timeline")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(toBuilder = true)
public class SubTimeline extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // 서브 타임라인 ID

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "main_timeline_id", nullable = false)
    private MainTimeline mainTimeline; // 해당 서브 타임라인이 속한 메인 타임라인

    @Column(nullable = false)
    private String title; // 서브 타임라인의 제목

    @Column(nullable = false, columnDefinition = "LONGTEXT")
    private String content; // 서브 타임라인의 내용

    @Column(nullable = false)
    private LocalDate startDate; // 시작 날짜

    @Column
    private LocalDate endDate; // 종료 날짜
}
