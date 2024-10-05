package com.api.RecordTimeline.domain.comment.controller;

import com.api.RecordTimeline.domain.comment.dto.request.CommentCreateRequestDTO;
import com.api.RecordTimeline.domain.comment.dto.response.CommentDeleteResponseDTO;
import com.api.RecordTimeline.domain.comment.dto.response.CommentResponseDTO;
import com.api.RecordTimeline.domain.comment.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/comments")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @PostMapping
    public ResponseEntity<CommentResponseDTO> createComment(@RequestBody CommentCreateRequestDTO request) {
        CommentResponseDTO response = commentService.createComment(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/sub-timeline/{subTimelineId}")
    public ResponseEntity<List<CommentResponseDTO>> getCommentsBySubTimelineId(@PathVariable Long subTimelineId) {
        List<CommentResponseDTO> comments = commentService.getCommentsBySubTimelineId(subTimelineId);
        return ResponseEntity.ok(comments);
    }

    @DeleteMapping("/{commentId}")
    public ResponseEntity<CommentDeleteResponseDTO> deleteComment(@PathVariable Long commentId) {
        CommentDeleteResponseDTO response = commentService.deleteComment(commentId);
        return ResponseEntity.ok(response);
    }
}

