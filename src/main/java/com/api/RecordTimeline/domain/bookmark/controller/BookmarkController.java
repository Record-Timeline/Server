package com.api.RecordTimeline.domain.bookmark.controller;

import com.api.RecordTimeline.domain.bookmark.dto.request.BookmarkRequestDTO;
import com.api.RecordTimeline.domain.bookmark.dto.response.BookmarkResponseDTO;
import com.api.RecordTimeline.domain.bookmark.dto.response.BookmarkedPostResponseDTO;
import com.api.RecordTimeline.domain.bookmark.service.BookmarkService;
import com.api.RecordTimeline.domain.member.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping("/my")
    public ResponseEntity<List<BookmarkedPostResponseDTO>> getMyBookmarkedPosts() {
        Member member = getCurrentAuthenticatedMember();
        List<BookmarkedPostResponseDTO> bookmarkedPosts = bookmarkService.getBookmarkedPosts(member);
        return ResponseEntity.ok(bookmarkedPosts);
    }

    private Member getCurrentAuthenticatedMember() {
        return bookmarkService.getCurrentAuthenticatedMember();
    }
}
