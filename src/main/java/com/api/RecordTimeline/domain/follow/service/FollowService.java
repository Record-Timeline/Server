package com.api.RecordTimeline.domain.follow.service;

import com.api.RecordTimeline.domain.follow.domain.Follow;
import com.api.RecordTimeline.domain.follow.repository.FollowRepository;
import com.api.RecordTimeline.domain.member.domain.Member;
import com.api.RecordTimeline.domain.member.repository.MemberRepository;
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

    @Transactional
    public void follow(Long followerId, Long followingId) {
        Member follower = memberRepository.findByIdAndIsDeletedFalse(followerId);
        Member following = memberRepository.findByIdAndIsDeletedFalse(followingId);

        if (follower == null || following == null) {
            throw new ApiException(ErrorType._USER_NOT_FOUND_DB);
        }

        // 이미 팔로우 관계가 존재하는지 확인
        Follow existingFollow = followRepository.findByFollowerIdAndFollowingId(followerId, followingId);
        if (existingFollow != null) {
            throw new ApiException(ErrorType._ALREADY_FOLLOWING);
        }

        Follow follow = new Follow(follower, following);
        followRepository.save(follow);
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
}
