package com.api.RecordTimeline.domain.comment.repository;

import com.api.RecordTimeline.domain.comment.domain.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findBySubTimelineId(Long subTimelineId);
}
