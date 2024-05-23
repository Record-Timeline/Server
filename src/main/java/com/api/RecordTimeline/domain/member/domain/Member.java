package com.api.RecordTimeline.domain.member.domain;

import com.api.RecordTimeline.domain.base.BaseEntity;
import com.api.RecordTimeline.domain.bookmark.domain.Bookmark;
import com.api.RecordTimeline.domain.follow.domain.Follow;
import com.api.RecordTimeline.domain.mainTimeline.domain.MainTimeline;
import com.api.RecordTimeline.domain.member.editor.MemberEditor;
import com.api.RecordTimeline.domain.profile.domain.Profile;
import com.api.RecordTimeline.domain.signup.email.domain.EmailCertification;
import com.api.RecordTimeline.domain.signup.signup.dto.request.BasicSignupRequestDto;
import com.api.RecordTimeline.domain.signup.signup.dto.request.KakaoSignupRequestDto;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

@Getter
@Builder(toBuilder = true)
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Member extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Email
    private String email;

    private String password;
    private String nickname;
    private String loginType;

    private boolean isDeleted = false;

    @OneToOne(fetch = FetchType.LAZY, mappedBy = "member", cascade = CascadeType.REMOVE)
    private Profile profile;

    @Enumerated(EnumType.STRING)
    private Interest interest;

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<MainTimeline> mainTimelines = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Bookmark> bookmarks = new ArrayList<>();

    @OneToMany(mappedBy = "follower")
    private List<Follow> followings = new ArrayList<>();

    @OneToMany(mappedBy = "following")
    private List<Follow> followers = new ArrayList<>();

    public MemberEditor.MemberEditorBuilder toEditor() {
        return MemberEditor.builder()
                .nickname(nickname)
                .interest(interest);
    }

    public void update(String newNickname, Interest newInterest) {
        if (StringUtils.hasText(newNickname)) {
            this.nickname = newNickname;
        }
        if (newInterest != null) {
            this.interest = newInterest;
        }
    }

    public Member(BasicSignupRequestDto basicDto) {
        this.email = basicDto.getEmail();
        this.password = basicDto.getPassword();
        this.nickname = basicDto.getNickname();
        this.interest = basicDto.getInterest();
        this.loginType = "app";
        this.isDeleted = false;
    }

    public Member(KakaoSignupRequestDto kakaoDto) {
        this.nickname = kakaoDto.getNickname();
        this.interest = kakaoDto.getInterest();
        this.loginType = "kakao";
        this.isDeleted = false;
    }

    public Member updatePassword(String newPassword, PasswordEncoder passwordEncoder) {
        return this.toBuilder()
                .password(passwordEncoder.encode(newPassword))
                .build();
    }

    @Builder
    public Member(String email, String password, String nickname, boolean isDeleted) {
        this.email = email;
        this.password = password;
        this.nickname = nickname;
        this.isDeleted = isDeleted;
        this.profile = new Profile(this, null, "");
    }

    public void markAsDeleted() {
        this.isDeleted = true;
    }
}
