package com.api.RecordTimeline.domain.like.controller;

import com.api.RecordTimeline.domain.like.dto.request.LikeRequestDTO;
import com.api.RecordTimeline.domain.like.dto.response.LikeResponseDTO;
import com.api.RecordTimeline.domain.like.service.LikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/likes")
public class LikeController {

    private final LikeService likeService;

    @PostMapping("/toggle")
    public ResponseEntity<LikeResponseDTO> toggleLike(@RequestBody LikeRequestDTO likeRequestDTO) {
        LikeResponseDTO responseDTO = likeService.toggleLike(likeRequestDTO);
        return ResponseEntity.ok(responseDTO);
    }
}
