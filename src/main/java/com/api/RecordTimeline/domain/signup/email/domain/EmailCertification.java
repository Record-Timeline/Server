package com.api.RecordTimeline.domain.signup.email.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;

@Getter
@Builder
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class EmailCertification {
    @Id
    private String memberId;
    private String email;
    private String certificationNumber;

}
