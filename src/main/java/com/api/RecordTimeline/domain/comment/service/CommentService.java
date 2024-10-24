package com.api.RecordTimeline.domain.comment.service;

import com.api.RecordTimeline.domain.comment.domain.Comment;
import com.api.RecordTimeline.domain.comment.dto.request.CommentCreateRequestDTO;
import com.api.RecordTimeline.domain.comment.dto.request.CommentUpdateRequestDTO;
import com.api.RecordTimeline.domain.comment.dto.response.CommentDeleteResponseDTO;
import com.api.RecordTimeline.domain.comment.dto.response.CommentResponseDTO;
import com.api.RecordTimeline.domain.comment.dto.response.CommentUpdateResponseDTO;
import com.api.RecordTimeline.domain.comment.repository.CommentRepository;
import com.api.RecordTimeline.domain.member.domain.Member;
import com.api.RecordTimeline.domain.member.repository.MemberRepository;
import com.api.RecordTimeline.domain.subTimeline.domain.SubTimeline;
import com.api.RecordTimeline.domain.subTimeline.repository.SubTimelineRepository;
import com.api.RecordTimeline.global.exception.ApiException;
import com.api.RecordTimeline.global.exception.ErrorType;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final SubTimelineRepository subTimelineRepository;
    private final MemberRepository memberRepository;

    // 댓글 생성 (로그인된 사용자만 가능)
    public CommentResponseDTO createComment(CommentCreateRequestDTO request) {
        Member currentMember = getCurrentAuthenticatedMember();

        SubTimeline subTimeline = subTimelineRepository.findById(request.getSubTimelineId())
                .orElseThrow(() -> new IllegalArgumentException("해당 서브타임라인을 찾을 수 없습니다."));


        Comment comment = Comment.builder()
                .subTimeline(subTimeline)
                .member(currentMember)
                .content(request.getContent())
                .build();

        Comment savedComment = commentRepository.save(comment);

        // 방어 코드: replies 필드가 null일 경우 빈 리스트로 초기화
        if (savedComment.getReplies() == null) {
            savedComment.setReplies(new ArrayList<>());
        }

        return new CommentResponseDTO(savedComment.getId(), savedComment.getContent(),
                savedComment.getCreatedDate().toString(), savedComment.getMember().getNickname(),
                savedComment.getReplies().size());
    }

    // 댓글 수정
    public CommentUpdateResponseDTO updateComment(Long commentId, CommentUpdateRequestDTO requestDTO) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new IllegalArgumentException("해당 댓글을 찾을 수 없습니다."));

        checkOwnership(comment.getMember().getEmail());

        comment.setContent(requestDTO.getContent());
        commentRepository.save(comment);
        return CommentUpdateResponseDTO.success();  // 수정 성공 시 성공 응답 반환
    }

    // 서브타임라인 ID로 댓글 조회
    public List<CommentResponseDTO> getCommentsBySubTimelineId(Long subTimelineId) {
        List<Comment> comments = commentRepository.findBySubTimelineId(subTimelineId);

        return comments.stream()
                .map(comment -> new CommentResponseDTO(
                        comment.getId(),
                        comment.getContent(),
                        comment.getCreatedDate().toString(),
                        comment.getMember().getNickname(),
                        comment.getReplies().size()))
                .collect(Collectors.toList());
    }

    // 댓글 삭제 (댓글 작성자 또는 서브타임라인 작성자만 가능)
    @Transactional
    public CommentDeleteResponseDTO deleteComment(Long commentId) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new IllegalArgumentException("해당 댓글을 찾을 수 없습니다."));

        checkOwnership(comment.getMember().getEmail());

        commentRepository.delete(comment);
        return CommentDeleteResponseDTO.success();  // 삭제 성공 시 성공 응답 반환
    }

    // 현재 인증된 사용자 가져오기
    private Member getCurrentAuthenticatedMember() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userEmail = authentication.getName();

        return memberRepository.findByEmailAndIsDeletedFalse(userEmail);
    }

    // 권한 체크 (작성자인지 확인)
    private void checkOwnership(String ownerEmail) {
        String userEmail = getCurrentAuthenticatedMember().getEmail();
        if (!userEmail.equals(ownerEmail)) {
            throw new ApiException(ErrorType._DO_NOT_HAVE_PERMISSION);
        }
    }
}
