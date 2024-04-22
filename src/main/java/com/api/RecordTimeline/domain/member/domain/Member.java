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

    private String name;
    private String password;
    private String nickname;
    private String loginType;

    private boolean isDeleted = false; // 탈퇴 여부 (탈퇴 시 db 자체를 삭제하지 않기 위함)


    @OneToOne(fetch = FetchType.LAZY, mappedBy = "member", cascade = CascadeType.REMOVE)
    private Profile profile;

    @Enumerated(EnumType.STRING)
    private Interest interest; //하나만 선택 가능

    @OneToMany(mappedBy = "member")
    private List<MainTimeline> mainTimelines = new ArrayList<>();

    //북마크 목록
    @Builder.Default
    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Bookmark> bookmarks = new ArrayList<>();

    // 팔로잉 목록
    @OneToMany(mappedBy = "follower")
    private List<Follow> followings = new ArrayList<>();

    // 팔로워 목록
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
        this.name = basicDto.getName();
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
        this.profile = new Profile(this, null, ""); // 초기 프로필 이미지, 소개글 없음
    }

    public void markAsDeleted() {
        this.isDeleted = true;
    }
}