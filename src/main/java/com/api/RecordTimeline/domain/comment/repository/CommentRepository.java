package com.api.RecordTimeline.domain.comment.repository;

import com.api.RecordTimeline.domain.comment.domain.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findBySubTimelineId(Long subTimelineId);
    @Query("SELECT COUNT(c) FROM Comment c WHERE c.subTimeline.id = :subTimelineId")
    int countBySubTimelineId(@Param("subTimelineId") Long subTimelineId); // 서브타임라인 ID로 댓글 수 조회
}
