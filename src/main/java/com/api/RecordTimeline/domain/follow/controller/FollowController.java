package com.api.RecordTimeline.domain.follow.controller;

import com.api.RecordTimeline.domain.follow.service.FollowService;
import com.api.RecordTimeline.domain.member.dto.response.MemberInfoResponseDto;
import com.api.RecordTimeline.global.success.SuccessResponse;
import com.api.RecordTimeline.global.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/follow")
@RequiredArgsConstructor
public class FollowController {

    private final FollowService followService;

    @PostMapping("/{followingId}")
    public SuccessResponse<Long> follow(@PathVariable Long followingId) {
        Long followerId = SecurityUtil.getCurrentMemberId();
        followService.follow(followerId, followingId);
        return new SuccessResponse<>(followingId);
    }

    @DeleteMapping("/{followingId}")
    public SuccessResponse<Long> unfollow(@PathVariable Long followingId) {
        Long followerId = SecurityUtil.getCurrentMemberId();
        followService.unfollow(followerId, followingId);
        return new SuccessResponse<>(followingId);
    }

    @GetMapping("/following")
    public SuccessResponse<List<MemberInfoResponseDto>> getFollowingList() {
        Long memberId = SecurityUtil.getCurrentMemberId();
        List<MemberInfoResponseDto> followingList = followService.getFollowingList(memberId).stream()
                .map(member -> MemberInfoResponseDto.fromMemberAndProfile(member, member.getProfile()))
                .collect(Collectors.toList());
        return new SuccessResponse<>(followingList);
    }

    @GetMapping("/followers")
    public SuccessResponse<List<MemberInfoResponseDto>> getFollowerList() {
        Long memberId = SecurityUtil.getCurrentMemberId();
        List<MemberInfoResponseDto> followerList = followService.getFollowerList(memberId).stream()
                .map(member -> MemberInfoResponseDto.fromMemberAndProfile(member, member.getProfile()))
                .collect(Collectors.toList());
        return new SuccessResponse<>(followerList);
    }

    @DeleteMapping("/remove-follower/{followerId}")
    public SuccessResponse<Long> removeFollower(@PathVariable Long followerId) {
        Long memberId = SecurityUtil.getCurrentMemberId();
        followService.removeFollower(memberId, followerId);
        return new SuccessResponse<>(followerId);
    }
}
