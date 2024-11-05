package com.api.RecordTimeline.domain.commentlike.service;

import com.api.RecordTimeline.domain.comment.domain.Comment;
import com.api.RecordTimeline.domain.comment.repository.CommentRepository;
import com.api.RecordTimeline.domain.commentlike.domain.CommentLike;
import com.api.RecordTimeline.domain.commentlike.repository.CommentLikeRepository;
import com.api.RecordTimeline.domain.like.dto.response.LikeResponseDTO;
import com.api.RecordTimeline.domain.member.domain.Member;
import com.api.RecordTimeline.domain.member.repository.MemberRepository;
import com.api.RecordTimeline.global.exception.ApiException;
import com.api.RecordTimeline.global.exception.ErrorType;
import com.api.RecordTimeline.global.security.jwt.JwtAuthenticationToken;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CommentLikeService {

    private final CommentLikeRepository commentLikeRepository;
    private final CommentRepository commentRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public LikeResponseDTO toggleCommentLike(Long commentId) {
        Member member = getCurrentAuthenticatedMember();
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new ApiException(ErrorType._COMMENT_NOT_FOUND));

        Optional<CommentLike> existingLike = commentLikeRepository.findByMemberAndComment(member, comment);

        if (existingLike.isPresent()) {
            commentLikeRepository.delete(existingLike.get());
            comment.adjustLikeCount(-1);
        } else {
            CommentLike commentLike = new CommentLike();
            commentLike.setMember(member);
            commentLike.setComment(comment);
            commentLikeRepository.save(commentLike);
            comment.adjustLikeCount(1);
            commentRepository.save(comment);
        }

        return new LikeResponseDTO("SU", "Success", comment.getLikeCount());
    }

    private Member getCurrentAuthenticatedMember() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Long memberId = ((JwtAuthenticationToken) authentication).getUserId();
        return memberRepository.findById(memberId)
                .orElseThrow(() -> new ApiException(ErrorType._USER_NOT_FOUND_DB));
    }

    public boolean isCommentLikedByMember(Long commentId, Member member) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new ApiException(ErrorType._COMMENT_NOT_FOUND));
        return commentLikeRepository.existsByMemberAndComment(member, comment);
    }

}
