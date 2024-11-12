package com.api.RecordTimeline.domain.replylike.controller;

import com.api.RecordTimeline.domain.like.dto.response.LikeResponseDTO;
import com.api.RecordTimeline.domain.member.domain.Member;
import com.api.RecordTimeline.domain.replylike.service.ReplyLikeService;
import com.api.RecordTimeline.global.exception.ApiException;
import com.api.RecordTimeline.global.exception.ErrorType;
import com.api.RecordTimeline.global.security.jwt.JwtAuthenticationToken;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/replies")
@RequiredArgsConstructor
public class ReplyLikeController {

    private final ReplyLikeService replyLikeService;

    @PostMapping("/{replyId}/like")
    public ResponseEntity<LikeResponseDTO> toggleReplyLike(@PathVariable Long replyId) {
        LikeResponseDTO response = replyLikeService.toggleReplyLike(replyId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{replyId}/like/status")
    public ResponseEntity<Boolean> getReplyLikeStatus(@PathVariable Long replyId) {
        boolean isLiked = replyLikeService.isReplyLikedByMember(replyId);
        return ResponseEntity.ok(isLiked);
    }
}
