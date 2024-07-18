package com.api.RecordTimeline.domain.bookmark.controller;

import com.api.RecordTimeline.domain.bookmark.dto.request.BookmarkRequestDTO;
import com.api.RecordTimeline.domain.bookmark.dto.response.BookmarkResponseDTO;
import com.api.RecordTimeline.domain.bookmark.service.BookmarkService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/bookmarks")
@RequiredArgsConstructor
public class BookmarkController {

    private final BookmarkService bookmarkService;

    @PostMapping("/toggle")
    public ResponseEntity<BookmarkResponseDTO> toggleBookmark(@RequestBody BookmarkRequestDTO bookmarkRequestDTO) {
        BookmarkResponseDTO response = bookmarkService.toggleBookmark(bookmarkRequestDTO);
        return ResponseEntity.ok(response);
    }
}
