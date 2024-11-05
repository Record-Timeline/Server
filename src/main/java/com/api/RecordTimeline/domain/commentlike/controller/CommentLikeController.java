package com.api.RecordTimeline.domain.commentlike.controller;

import com.api.RecordTimeline.domain.commentlike.service.CommentLikeService;
import com.api.RecordTimeline.domain.like.dto.response.LikeResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/comments")
@RequiredArgsConstructor
public class CommentLikeController {

    private final CommentLikeService commentLikeService;

    @PostMapping("/{commentId}/like")
    public ResponseEntity<LikeResponseDTO> toggleCommentLike(@PathVariable Long commentId) {
        LikeResponseDTO response = commentLikeService.toggleCommentLike(commentId);
        return ResponseEntity.ok(response);
    }
}
