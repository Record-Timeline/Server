package com.api.RecordTimeline.domain.subTimeline.domain;

import com.api.RecordTimeline.domain.base.BaseEntity;
import com.api.RecordTimeline.domain.mainTimeline.domain.MainTimeline;
import jakarta.persistence.*;
import lombok.*;

/** 연관관계를 매핑을 위해 작성. 완성된 코드 X **/
@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class SubTimeline extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "main_timeline_id")
    private MainTimeline mainTimeline;
}
