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

    @GetMapping("/my-following")
    public SuccessResponse<List<MemberInfoResponseDto>> getFollowingList() {
        Long memberId = SecurityUtil.getCurrentMemberId();
        List<MemberInfoResponseDto> followingList = followService.getFollowingList(memberId).stream()
                .map(member -> {
                    Long followerCount = followService.getFollowerCountForMember(member.getId());
                    Long followingCount = followService.getFollowingCountForMember(member.getId());
                    return MemberInfoResponseDto.fromMemberAndProfile(member, member.getProfile(), followerCount, followingCount);
                })
                .collect(Collectors.toList());
        return new SuccessResponse<>(followingList);
    }

    @GetMapping("/my-followers")
    public SuccessResponse<List<MemberInfoResponseDto>> getFollowerList() {
        Long memberId = SecurityUtil.getCurrentMemberId();
        List<MemberInfoResponseDto> followerList = followService.getFollowerList(memberId).stream()
                .map(member -> {
                    Long followerCount = followService.getFollowerCountForMember(member.getId());
                    Long followingCount = followService.getFollowingCountForMember(member.getId());
                    return MemberInfoResponseDto.fromMemberAndProfile(member, member.getProfile(), followerCount, followingCount);
                })
                .collect(Collectors.toList());
        return new SuccessResponse<>(followerList);
    }

    @DeleteMapping("/remove-follower/{followerId}")
    public SuccessResponse<Long> removeFollower(@PathVariable Long followerId) {
        Long memberId = SecurityUtil.getCurrentMemberId();
        followService.removeFollower(memberId, followerId);
        return new SuccessResponse<>(followerId);
    }

    @GetMapping("/my-count/following")
    public SuccessResponse<Long> getFollowingCount() {
        Long memberId = SecurityUtil.getCurrentMemberId();
        Long followingCount = followService.getFollowingCount(memberId);
        return new SuccessResponse<>(followingCount);
    }

    @GetMapping("/my-count/followers")
    public SuccessResponse<Long> getFollowerCount() {
        Long memberId = SecurityUtil.getCurrentMemberId();
        Long followerCount = followService.getFollowerCount(memberId);
        return new SuccessResponse<>(followerCount);
    }

    @GetMapping("/count/following/{memberId}")
    public SuccessResponse<Long> getFollowingCountForMember(@PathVariable Long memberId) {
        Long followingCount = followService.getFollowingCountForMember(memberId);
        return new SuccessResponse<>(followingCount);
    }

    @GetMapping("/count/followers/{memberId}")
    public SuccessResponse<Long> getFollowerCountForMember(@PathVariable Long memberId) {
        Long followerCount = followService.getFollowerCountForMember(memberId);
        return new SuccessResponse<>(followerCount);
    }

    @GetMapping("/is-following/{memberId}")
    public SuccessResponse<Boolean> isFollowing(@PathVariable Long memberId) {
        Long followerId;
        try {
            followerId = SecurityUtil.getCurrentMemberId();
        } catch (RuntimeException e) {
            return new SuccessResponse<>(false); // 익명 사용자는 팔로우를 할 수 없으므로 false 반환
        }

        boolean isFollowing = followService.isFollowing(followerId, memberId);
        return new SuccessResponse<>(isFollowing);
    }
}
