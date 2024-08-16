package com.api.RecordTimeline.domain.career.domain;

import com.api.RecordTimeline.domain.base.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Builder(toBuilder = true)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class ForeignLanguage extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String languageName;

    @Enumerated(EnumType.STRING)
    private Proficiency proficiency;

    private String userEmail;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "career_detail_id")
    private CareerDetail careerDetail;

    public ForeignLanguage update(ForeignLanguage newLanguage) {
        return ForeignLanguage.builder()
                .id(this.id)
                .languageName(newLanguage.getLanguageName())
                .proficiency(newLanguage.getProficiency())
                .userEmail(this.userEmail)  // 이메일 필드는 변경되지 않도록 설정4
                .careerDetail(this.careerDetail)
                .build();
    }
}
