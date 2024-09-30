package com.api.RecordTimeline.domain.reply.controller;

import com.api.RecordTimeline.domain.reply.dto.request.ReplyCreateRequestDTO;
import com.api.RecordTimeline.domain.reply.dto.response.ReplyResponseDTO;
import com.api.RecordTimeline.domain.reply.service.ReplyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/replies")
@RequiredArgsConstructor
public class ReplyController {

    private final ReplyService replyService;

    @PostMapping
    public ResponseEntity<ReplyResponseDTO> createReply(@RequestBody ReplyCreateRequestDTO request) {
        ReplyResponseDTO response = replyService.createReply(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/comment/{commentId}")
    public ResponseEntity<List<ReplyResponseDTO>> getRepliesByCommentId(@PathVariable Long commentId) {
        List<ReplyResponseDTO> replies = replyService.getRepliesByCommentId(commentId);
        return ResponseEntity.ok(replies);
    }

    @DeleteMapping("/{replyId}")
    public ResponseEntity<Void> deleteReply(@PathVariable Long replyId) {
        replyService.deleteReply(replyId);
        return ResponseEntity.noContent().build();
    }
}

