package com.api.RecordTimeline.domain.follow.service;

import com.api.RecordTimeline.domain.follow.domain.Follow;
import com.api.RecordTimeline.domain.follow.repository.FollowRepository;
import com.api.RecordTimeline.domain.member.domain.Member;
import com.api.RecordTimeline.domain.member.repository.MemberRepository;
import com.api.RecordTimeline.domain.notification.domain.NotificationType;
import com.api.RecordTimeline.domain.notification.dto.RelateInfoDto;
import com.api.RecordTimeline.domain.notification.service.NotificationService;
import com.api.RecordTimeline.global.exception.ApiException;
import com.api.RecordTimeline.global.exception.ErrorType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FollowService {

    private final FollowRepository followRepository;
    private final MemberRepository memberRepository;
    private final NotificationService notificationService;

    @Transactional
    public void follow(Long followerId, Long followingId) {
        Member follower = memberRepository.findByIdAndIsDeletedFalse(followerId);
        Member following = memberRepository.findByIdAndIsDeletedFalse(followingId);

        if (follower == null || following == null) {
            throw new ApiException(ErrorType._USER_NOT_FOUND_DB);
        }

        Follow existingFollow = followRepository.findByFollowerIdAndFollowingId(followerId, followingId);
        if (existingFollow != null) {
            throw new ApiException(ErrorType._ALREADY_FOLLOWING);
        }

        Follow follow = new Follow(follower, following);
        followRepository.save(follow);

        // 알림 메시지 생성
        String message = follower.getNickname() + " 님이 당신을 팔로우했습니다.";

        // RelateInfoDto를 생성하여 팔로우 알림에 전달
        notificationService.sendNotification(
                follower,
                following,
                message,
                NotificationType.FOLLOW,
                new RelateInfoDto(null, follower.getId(), null) // postId와 mainTimelineId는 null로 설정
        );
    }


    @Transactional
    public void unfollow(Long followerId, Long followingId) {
        Follow follow = followRepository.findByFollowerIdAndFollowingId(followerId, followingId);

        if (follow == null) {
            throw new ApiException(ErrorType._FOLLOW_NOT_FOUND);
        }

        followRepository.delete(follow);
    }

    @Transactional
    public void removeFollower(Long memberId, Long followerId) {
        Follow follow = followRepository.findByFollowerIdAndFollowingId(followerId, memberId);

        if (follow == null) {
            throw new ApiException(ErrorType._FOLLOW_NOT_FOUND);
        }

        followRepository.delete(follow);
    }

    public List<Member> getFollowingList(Long memberId) {
        return followRepository.findFollowingByFollowerId(memberId);
    }

    public List<Member> getFollowerList(Long memberId) {
        return followRepository.findFollowerByFollowingId(memberId);
    }

    public Long getFollowingCount(Long memberId) {
        return followRepository.countFollowingByFollowerId(memberId);
    }

    public Long getFollowerCount(Long memberId) {
        return followRepository.countFollowersByFollowingId(memberId);
    }

    public Long getFollowingCountForMember(Long memberId) {
        return followRepository.countFollowingByFollowerId(memberId);
    }

    public Long getFollowerCountForMember(Long memberId) {
        return followRepository.countFollowersByFollowingId(memberId);
    }

    public boolean isFollowing(Long followerId, Long followingId) {
        Follow follow = followRepository.findByFollowerIdAndFollowingId(followerId, followingId);
        return follow != null;
    }
}

