package com.api.RecordTimeline.domain.like.repository;

import com.api.RecordTimeline.domain.like.domain.UserLike;
import com.api.RecordTimeline.domain.member.domain.Member;
import com.api.RecordTimeline.domain.subTimeline.domain.SubTimeline;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LikeRepository extends JpaRepository<UserLike, Long> {
    Optional<UserLike> findByMemberAndSubTimeline(Member member, SubTimeline subTimeline);
}
