package com.api.RecordTimeline.domain.signup.email.domain;

import com.api.RecordTimeline.domain.member.domain.Member;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.*;

@Getter
@Builder
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class EmailCertification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @Email
    private String email;

    private String certificationNumber;

    public EmailCertification(String email, String certificationNumber) {

    }
}
