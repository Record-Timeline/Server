package com.api.RecordTimeline.domain.replylike.repository;

import com.api.RecordTimeline.domain.member.domain.Member;
import com.api.RecordTimeline.domain.reply.domain.Reply;
import com.api.RecordTimeline.domain.replylike.domain.ReplyLike;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ReplyLikeRepository extends JpaRepository<ReplyLike, Long> {
    Optional<ReplyLike> findByMemberAndReply(Member member, Reply reply);
    void deleteByReply(Reply reply);
}

