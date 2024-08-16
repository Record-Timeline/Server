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
public class Education extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String degree;

    @Column(nullable = false)
    private String institution;

    @Column(nullable = false)
    private LocalDate startDate;

    @Column
    private LocalDate endDate;

    private String userEmail;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "career_detail_id")
    private CareerDetail careerDetail;

    public Education update(Education newEducation) {
        return Education.builder()
                .id(this.id)
                .degree(newEducation.getDegree())
                .institution(newEducation.getInstitution())
                .startDate(newEducation.getStartDate())
                .endDate(newEducation.getEndDate())
                .userEmail(this.userEmail)  // 이메일 필드는 변경되지 않도록 설정
                .build();
    }
}
