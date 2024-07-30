package com.api.RecordTimeline.domain.signup.email.domain;

import com.api.RecordTimeline.domain.member.domain.Member;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.*;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class EmailCertification {

    @Id
    @Email
    private String email;

    private String certificationNumber;

    @Column(nullable = false)
    private String context; // SIGNUP, PASSWORD_RESET

    @Column(nullable = false)
    private boolean verified = false;

    public void markAsVerified() {
        this.verified = true;
    }

    public EmailCertification(String email, String certificationNumber, String context) {
        this.email = email;
        this.certificationNumber = certificationNumber;
        this.context = context;
    }
}
