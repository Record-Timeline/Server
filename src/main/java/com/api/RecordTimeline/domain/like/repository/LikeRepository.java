package com.api.RecordTimeline.domain.like.repository;

import com.api.RecordTimeline.domain.like.domain.UserLike;
import com.api.RecordTimeline.domain.member.domain.Member;
import com.api.RecordTimeline.domain.subTimeline.domain.SubTimeline;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface LikeRepository extends JpaRepository<UserLike, Long> {

    // 좋아요 조회 메서드 - 수정이나 삭제 작업을 하지 않음
    Optional<UserLike> findByMemberAndSubTimeline(Member member, SubTimeline subTimeline);

    // 서브타임라인에 해당하는 좋아요를 삭제하는 메서드
    @Modifying
    @Transactional
    @Query("DELETE FROM UserLike l WHERE l.subTimeline = :subTimeline")
    void deleteBySubTimeline(@Param("subTimeline") SubTimeline subTimeline);
}

