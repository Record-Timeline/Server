package com.api.RecordTimeline.domain.subTimeline.domain;

import com.api.RecordTimeline.domain.base.BaseEntity;
import com.api.RecordTimeline.domain.mainTimeline.domain.MainTimeline;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

/** 연관관계를 매핑을 위해 작성. 완성된 코드 X **/
@Entity
@Table(name = "sub_timeline")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED) //JPA 스펙에 맞춘 기본 생성자
@AllArgsConstructor(access = AccessLevel.PRIVATE) // 빌더를 사용하기 위해 모든 인자를 받는 생성자를 private으로 설정
public class SubTimeline extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "main_timeline_id", nullable = false) // 메인 타임라인과의 연관관계 명시
    private MainTimeline mainTimeline;

    @Column(nullable = false)
    private String title; // 서브 타임라인의 제목

    @Column(nullable = false, length = 1000)
    private String content; // 서브 타임라인의 내용

    @ElementCollection // 간단한 값 목록을 저장할 때 사용
    @CollectionTable(name = "sub_timeline_images", joinColumns = @JoinColumn(name = "sub_timeline_id"))
    @Column(name = "image_url")
    private List<String> imageUrls; // 이미지 URL 목록

    @Builder // 빌더 패턴을 사용하여 객체 생성을 간편하게 처리
    public SubTimeline(MainTimeline mainTimeline, String title, String content, List<String> imageUrls) {
        this.mainTimeline = mainTimeline;
        this.title = title;
        this.content = content;
        this.imageUrls = imageUrls;
    }
}
