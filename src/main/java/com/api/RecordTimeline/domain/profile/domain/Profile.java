package com.api.RecordTimeline.domain.profile.domain;

import com.api.RecordTimeline.domain.base.BaseEntity;
import com.api.RecordTimeline.domain.member.domain.Member;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Profile extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", unique = true)
    private Member member;

    private String profileImgUrl;

    private String introduction;

    public Profile(Member member, String profileImgUrl, String introduction) {
        this.member = member;
        this.profileImgUrl = profileImgUrl;
        this.introduction = introduction;
    }

    public static Profile of(final Member member, final String profileImgUrl, final String introduction) {
        return Profile.builder()
                .member(member)
                .profileImgUrl(profileImgUrl)
                .introduction(introduction)
                .build();
    }
    public void changeProfileImage(String profileImgUrl) {
        this.profileImgUrl = profileImgUrl;
    }

    public void deleteProfileImage() {
        this.profileImgUrl = null;
    }

}
