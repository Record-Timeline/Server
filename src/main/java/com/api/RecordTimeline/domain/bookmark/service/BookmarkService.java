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
import com.api.RecordTimeline.global.exception.ApiException;
import com.api.RecordTimeline.global.exception.ErrorType;
import com.api.RecordTimeline.global.security.jwt.JwtAuthenticationToken;
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
        // 서브타임라인 존재 여부 확인 및 예외 처리
        SubTimeline subTimeline = subTimelineRepository.findById(subTimelineId)
                .orElseThrow(() -> new ApiException(ErrorType._SUBTIMELINE_NOT_FOUND));

        Optional<Bookmark> existingBookmark = bookmarkRepository.findByMemberAndSubTimeline(member, subTimeline);

        if (existingBookmark.isPresent()) {
            bookmarkRepository.delete(existingBookmark.get());
            subTimeline.adjustBookmarkCount(-1); // 북마크 수 감소 및 검증
            subTimelineRepository.save(subTimeline); // 변경된 북마크 수 저장
            return BookmarkResponseDTO.success("removed successfully",subTimeline.getBookmarkCount()); // 북마크 해제 성공 응답
        } else {
            Bookmark bookmark = Bookmark.builder()
                    .member(member)
                    .subTimeline(subTimeline)
                    .build();
            bookmarkRepository.save(bookmark);
            subTimeline.adjustBookmarkCount(1); // 북마크 수 증가 및 검증
            subTimelineRepository.save(subTimeline); // 변경된 북마크 수 저장
            return BookmarkResponseDTO.success("added successfully",subTimeline.getBookmarkCount()); // 북마크 추가 성공 응답
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
        JwtAuthenticationToken jwtToken = (JwtAuthenticationToken) authentication;
        Long memberId = jwtToken.getUserId();
        Member member = memberRepository.findByIdAndIsDeletedFalse(memberId);
        if (member == null) {
            throw new ApiException(ErrorType._USER_NOT_FOUND_DB);
        }
        return member;
    }
}
