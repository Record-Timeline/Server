package com.api.RecordTimeline.domain.bookmark.repository;

import com.api.RecordTimeline.domain.bookmark.domain.Bookmark;
import com.api.RecordTimeline.domain.member.domain.Member;
import com.api.RecordTimeline.domain.subTimeline.domain.SubTimeline;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.List;

public interface BookmarkRepository extends JpaRepository<Bookmark, Long> {

    // 북마크 조회 메서드 - 수정이나 삭제 작업을 하지 않음
    // Member와 SubTimeline을 기반으로 북마크를 찾는 메서드
    Optional<Bookmark> findByMemberAndSubTimeline(Member member, SubTimeline subTimeline);

    // 특정 회원이 북마크한 모든 게시물을 가져오는 메서드
    List<Bookmark> findByMember(Member member);

    // 서브타임라인에 해당하는 북마크를 삭제하는 메서드
    @Modifying
    @Transactional
    @Query("DELETE FROM Bookmark b WHERE b.subTimeline = :subTimeline")
    void deleteBySubTimeline(@Param("subTimeline") SubTimeline subTimeline);
}
