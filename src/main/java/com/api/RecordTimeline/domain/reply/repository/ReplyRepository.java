package com.api.RecordTimeline.domain.reply.repository;

import com.api.RecordTimeline.domain.reply.domain.Reply;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReplyRepository extends JpaRepository<Reply, Long> {
    List<Reply> findByCommentId(Long commentId);
    @Query("SELECT COUNT(r) FROM Reply r WHERE r.comment.subTimeline.id = :subTimelineId")
    int countByComment_SubTimelineId(@Param("subTimelineId") Long subTimelineId); // 서브타임라인 ID로 대댓글 수 조회
}

