package com.api.RecordTimeline.domain.member.dto.response;

import com.api.RecordTimeline.domain.member.domain.Member;
import com.api.RecordTimeline.domain.profile.domain.Profile;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class MemberInfoResponseDto {
    private final Long memberId;
    private final String email;
    private final String nickname;
    private final String interest;
    private final String profileImageUrl;
    private final String introduction;

    public static MemberInfoResponseDto fromMemberAndProfile(Member member, Profile profile) {
        return MemberInfoResponseDto.builder()
                .memberId(member.getId())
                .email(member.getEmail())
                .nickname(member.getNickname())
                .interest(member.getInterest() != null ? member.getInterest().toString() : "")
                .profileImageUrl(profile != null ? profile.getProfileImgUrl() : "")
                .introduction(profile != null ? profile.getIntroduction() : "")
                .build();
    }
}
