package com.api.RecordTimeline.domain.follow.repository;

import com.api.RecordTimeline.domain.follow.domain.Follow;
import com.api.RecordTimeline.domain.member.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface FollowRepository extends JpaRepository<Follow, Long> {
    Follow findByFollowerIdAndFollowingId(Long followerId, Long followingId);

    @Query("SELECT f.following FROM Follow f WHERE f.follower.id = :memberId")
    List<Member> findFollowingByFollowerId(Long memberId);

    @Query("SELECT f.follower FROM Follow f WHERE f.following.id = :memberId")
    List<Member> findFollowerByFollowingId(Long memberId);

    @Query("SELECT COUNT(f) FROM Follow f WHERE f.follower.id = :memberId")
    Long countFollowingByFollowerId(Long memberId);

    @Query("SELECT COUNT(f) FROM Follow f WHERE f.following.id = :memberId")
    Long countFollowersByFollowingId(Long memberId);

}
