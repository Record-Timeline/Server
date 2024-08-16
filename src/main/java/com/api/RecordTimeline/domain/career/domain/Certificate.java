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
public class Certificate extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private LocalDate date;

    private String userEmail;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "career_detail_id")
    private CareerDetail careerDetail;

    public Certificate update(Certificate newCertificate) {
        return Certificate.builder()
                .id(this.id)
                .name(newCertificate.getName())
                .date(newCertificate.getDate().withDayOfMonth(1))
                .userEmail(this.userEmail)  // 이메일 필드는 변경되지 않도록 설정
                .careerDetail(this.careerDetail)
                .build();
    }

    public Certificate withDate(LocalDate date) {
        return this.toBuilder().date(date).build();
    }
}
