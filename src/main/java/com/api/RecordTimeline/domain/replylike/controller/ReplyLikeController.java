package com.api.RecordTimeline.domain.replylike.controller;

import com.api.RecordTimeline.domain.like.dto.response.LikeResponseDTO;
import com.api.RecordTimeline.domain.replylike.service.ReplyLikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
