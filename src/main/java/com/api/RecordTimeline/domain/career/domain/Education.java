package com.api.RecordTimeline.domain.career.domain;

import com.api.RecordTimeline.domain.base.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Education extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String institution;
    private String degree;
    private String startDate;
    private String endDate;

    public Education update(Education newEducation) {
        return Education.builder()
                .id(this.id)
                .institution(newEducation.getInstitution())
                .degree(newEducation.getDegree())
                .startDate(newEducation.getStartDate())
                .endDate(newEducation.getEndDate())
                .build();
    }
}
