package com.api.RecordTimeline.domain.career.domain;

import com.api.RecordTimeline.domain.base.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Getter
@Builder(toBuilder = true)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Career extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String companyName;

    private String duty;

    private String position;

    @Column(nullable = false)
    private LocalDate startDate;

    @Column
    private LocalDate endDate;

    private String userEmail;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "career_detail_id")
    private CareerDetail careerDetail;

    public Career update(Career newCareer) {
        return Career.builder()
                .id(this.id)
                .companyName(newCareer.getCompanyName())
                .position(newCareer.getPosition())
                .duty(newCareer.getDuty())
                .startDate(newCareer.getStartDate())
                .endDate(newCareer.getEndDate())
                .userEmail(this.userEmail)  // 이메일 필드는 변경되지 않도록 설정
                .build();
    }
}
