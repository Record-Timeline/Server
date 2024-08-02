package com.api.RecordTimeline.domain.career.domain;

import com.api.RecordTimeline.domain.base.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Career extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String companyName;
    private String position;
    private String startDate;
    private String endDate;

    public Career update(Career newCareer) {
        return Career.builder()
                .id(this.id)
                .companyName(newCareer.getCompanyName())
                .position(newCareer.getPosition())
                .startDate(newCareer.getStartDate())
                .endDate(newCareer.getEndDate())
                .build();
    }
}
