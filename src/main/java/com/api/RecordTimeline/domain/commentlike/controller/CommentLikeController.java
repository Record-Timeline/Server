package com.api.RecordTimeline.domain.commentlike.controller;

import com.api.RecordTimeline.domain.commentlike.service.CommentLikeService;
import com.api.RecordTimeline.domain.like.dto.response.LikeResponseDTO;
import com.api.RecordTimeline.domain.member.domain.Member;
import com.api.RecordTimeline.domain.member.repository.MemberRepository;
import com.api.RecordTimeline.global.exception.ApiException;
import com.api.RecordTimeline.global.exception.ErrorType;
import com.api.RecordTimeline.global.security.jwt.JwtAuthenticationToken;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/comments")
@RequiredArgsConstructor
public class CommentLikeController {

    private final CommentLikeService commentLikeService;
    private final MemberRepository memberRepository;

    @PostMapping("/{commentId}/like")
    public ResponseEntity<LikeResponseDTO> toggleCommentLike(@PathVariable Long commentId) {
        LikeResponseDTO response = commentLikeService.toggleCommentLike(commentId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{commentId}/like/status")
    public ResponseEntity<Boolean> getCommentLikeStatus(@PathVariable Long commentId) {
        Member member = getCurrentAuthenticatedMember();
        boolean isLiked = commentLikeService.isCommentLikedByMember(commentId, member);
        return ResponseEntity.ok(isLiked);
    }

    // 현재 인증된 사용자를 가져오는 메서드 추가
    private Member getCurrentAuthenticatedMember() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Long memberId = ((JwtAuthenticationToken) authentication).getUserId();
        return memberRepository.findById(memberId)
                .orElseThrow(() -> new ApiException(ErrorType._USER_NOT_FOUND_DB));
    }

}
