package com.api.RecordTimeline.domain.member.domain;

import com.api.RecordTimeline.domain.base.BaseEntity;
import com.api.RecordTimeline.domain.bookmark.domain.Bookmark;
import com.api.RecordTimeline.domain.follow.domain.Follow;
import com.api.RecordTimeline.domain.mainTimeline.domain.MainTimeline;
import com.api.RecordTimeline.domain.member.editor.MemberEditor;
import com.api.RecordTimeline.domain.profile.domain.Profile;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Builder
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
    private String phoneNumber;


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
}