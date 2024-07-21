package com.api.RecordTimeline.domain.bookmark.service;

import com.api.RecordTimeline.domain.bookmark.domain.Bookmark;
import com.api.RecordTimeline.domain.bookmark.dto.request.BookmarkRequestDTO;
import com.api.RecordTimeline.domain.bookmark.dto.response.BookmarkResponseDTO;
import com.api.RecordTimeline.domain.bookmark.dto.response.BookmarkedPostResponseDTO;
import com.api.RecordTimeline.domain.bookmark.repository.BookmarkRepository;
import com.api.RecordTimeline.domain.member.domain.Member;
import com.api.RecordTimeline.domain.member.repository.MemberRepository;
import com.api.RecordTimeline.domain.subTimeline.domain.SubTimeline;
import com.api.RecordTimeline.domain.subTimeline.repository.SubTimelineRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookmarkService {

    private final BookmarkRepository bookmarkRepository;
    private final MemberRepository memberRepository;
    private final SubTimelineRepository subTimelineRepository;

    public BookmarkResponseDTO toggleBookmark(BookmarkRequestDTO bookmarkRequestDTO) {
        Long subTimelineId = bookmarkRequestDTO.getSubTimelineId();
        Member member = getCurrentAuthenticatedMember();
        SubTimeline subTimeline = subTimelineRepository.findById(subTimelineId)
                .orElseThrow(() -> new NoSuchElementException("SubTimeline not found"));

        Optional<Bookmark> existingBookmark = bookmarkRepository.findByMemberAndSubTimeline(member, subTimeline);

        if (existingBookmark.isPresent()) {
            bookmarkRepository.delete(existingBookmark.get());
            subTimeline.adjustBookmarkCount(-1); // 북마크 수 감소 및 검증
            subTimelineRepository.save(subTimeline); // 변경된 북마크 수 저장
            return BookmarkResponseDTO.success("Bookmark removed successfully",subTimeline.getBookmarkCount()); // 북마크 해제 성공 응답
        } else {
            Bookmark bookmark = Bookmark.builder()
                    .member(member)
                    .subTimeline(subTimeline)
                    .build();
            bookmarkRepository.save(bookmark);
            subTimeline.adjustBookmarkCount(1); // 북마크 수 증가 및 검증
            subTimelineRepository.save(subTimeline); // 변경된 북마크 수 저장
            return BookmarkResponseDTO.success("Success",subTimeline.getBookmarkCount()); // 북마크 추가 성공 응답
        }
    }

    public List<BookmarkedPostResponseDTO> getBookmarkedPosts(Member member) {
        List<Bookmark> bookmarks = bookmarkRepository.findByMember(member);

        return bookmarks.stream()
                .map(bookmark -> {
                    SubTimeline subTimeline = bookmark.getSubTimeline();
                    return new BookmarkedPostResponseDTO(
                            subTimeline.getId(),
                            subTimeline.getTitle(),
                            subTimeline.getContent(),
                            subTimeline.getStartDate(),
                            subTimeline.getEndDate(),
                            subTimeline.getBookmarkCount(),
                            subTimeline.getMember().getNickname(),
                            subTimeline.getMember().getInterest().name()
                    );
                })
                .collect(Collectors.toList());
    }

    public Member getCurrentAuthenticatedMember() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userEmail = authentication.getName();
        return Optional.ofNullable(memberRepository.findByEmailAndIsDeletedFalse(userEmail))
                .orElseThrow(() -> new NoSuchElementException("활성 상태의 해당 이메일로 등록된 사용자를 찾을 수 없습니다: " + userEmail));
    }
}
