package com.api.RecordTimeline.domain.follow.controller;

import com.api.RecordTimeline.domain.follow.service.FollowService;
import com.api.RecordTimeline.domain.member.dto.response.MemberInfoResponseDto;
import com.api.RecordTimeline.domain.member.service.MemberServiceImpl;
import com.api.RecordTimeline.global.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/follow")
@RequiredArgsConstructor
public class FollowController {

    private final FollowService followService;
    private final SecurityUtil securityUtil;

    @PostMapping("/{followingId}")
    public ResponseEntity<Void> follow(@PathVariable Long followingId) {
        Long followerId = SecurityUtil.getCurrentMemberId();
        followService.follow(followerId, followingId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{followingId}")
    public ResponseEntity<Void> unfollow(@PathVariable Long followingId) {
        Long followerId = SecurityUtil.getCurrentMemberId();
        followService.unfollow(followerId, followingId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/following")
    public ResponseEntity<List<MemberInfoResponseDto>> getFollowingList() {
        Long memberId = SecurityUtil.getCurrentMemberId();
        List<MemberInfoResponseDto> followingList = followService.getFollowingList(memberId).stream()
                .map(member -> MemberInfoResponseDto.fromMemberAndProfile(member, member.getProfile()))
                .collect(Collectors.toList());
        return ResponseEntity.ok(followingList);
    }

    @GetMapping("/followers")
    public ResponseEntity<List<MemberInfoResponseDto>> getFollowerList() {
        Long memberId = SecurityUtil.getCurrentMemberId();
        List<MemberInfoResponseDto> followerList = followService.getFollowerList(memberId).stream()
                .map(member -> MemberInfoResponseDto.fromMemberAndProfile(member, member.getProfile()))
                .collect(Collectors.toList());
        return ResponseEntity.ok(followerList);
    }

    @DeleteMapping("/remove-follower/{followerId}")
    public ResponseEntity<Void> removeFollower(@PathVariable Long followerId) {
        Long memberId = SecurityUtil.getCurrentMemberId();
        followService.removeFollower(memberId, followerId);
        return ResponseEntity.ok().build();
    }
}
