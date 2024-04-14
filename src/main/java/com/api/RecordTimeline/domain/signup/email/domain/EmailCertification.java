package com.api.RecordTimeline.domain.signup.email.domain;

import com.api.RecordTimeline.domain.member.domain.Member;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.*;

@Getter
@Builder
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class EmailCertification {

    @Id
    @Email
    private String email;

    private String certificationNumber;

    public EmailCertification(String email, String certificationNumber) {
        this.email = email;
        this.certificationNumber = certificationNumber;
    }
}
