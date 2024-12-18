package com.api.RecordTimeline.domain.reply.service;

import com.api.RecordTimeline.domain.comment.domain.Comment;
import com.api.RecordTimeline.domain.comment.repository.CommentRepository;
import com.api.RecordTimeline.domain.member.domain.Member;
import com.api.RecordTimeline.domain.member.repository.MemberRepository;
import com.api.RecordTimeline.domain.notification.domain.NotificationType;
import com.api.RecordTimeline.domain.notification.dto.RelateInfoDto;
import com.api.RecordTimeline.domain.notification.service.NotificationService;
import com.api.RecordTimeline.domain.reply.domain.Reply;
import com.api.RecordTimeline.domain.reply.dto.request.ReplyCreateRequestDTO;
import com.api.RecordTimeline.domain.reply.dto.request.ReplyUpdateRequestDTO;
import com.api.RecordTimeline.domain.reply.dto.response.ReplyDeleteResponseDTO;
import com.api.RecordTimeline.domain.reply.dto.response.ReplyResponseDTO;
import com.api.RecordTimeline.domain.reply.dto.response.ReplyUpdateResponseDTO;
import com.api.RecordTimeline.domain.reply.repository.ReplyRepository;
import com.api.RecordTimeline.global.exception.ApiException;
import com.api.RecordTimeline.global.exception.ErrorType;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReplyService {

    private final ReplyRepository replyRepository;
    private final CommentRepository commentRepository;
    private final MemberRepository memberRepository;
    private final NotificationService notificationService;

    // 대댓글 생성 (로그인된 사용자만 가능)
    public ReplyResponseDTO createReply(ReplyCreateRequestDTO request) {
        Member currentMember = getCurrentAuthenticatedMember();

        // 댓글 존재 여부 확인
        Comment comment = commentRepository.findById(request.getCommentId())
                .orElseThrow(() -> new ApiException(ErrorType._COMMENT_NOT_FOUND, "해당 댓글을 찾을 수 없습니다."));

        // 대댓글 생성 및 저장
        Reply reply = Reply.builder()
                .comment(comment) // 댓글과의 연관관계 설정
                .member(currentMember)
                .content(request.getContent())
                .build();

        Reply savedReply = replyRepository.save(reply);

        // 알림 전송 로직
        notificationService.sendNotification(
                currentMember, // 알림을 보낸 사용자 (대댓글 작성자)
                comment.getMember(), // 알림을 받을 사용자 (댓글 작성자)
                currentMember.getNickname() + "님이 당신의 댓글에 대댓글을 남겼습니다.",
                NotificationType.REPLY,
                new RelateInfoDto(
                        comment.getSubTimeline().getId(),
                        comment.getMember().getId(),
                        comment.getSubTimeline().getMainTimeline().getId()
                )
        );

        // 게시글 작성자 식별
        Member postOwner = comment.getSubTimeline().getMainTimeline().getMember();

        // 댓글 작성자와 게시글 작성자가 다를 경우 게시글 작성자에게 알림 전송
        if (!postOwner.equals(comment.getMember())) {
            notificationService.sendNotification(
                    currentMember, // 알림을 보낸 사용자 (대댓글 작성자)
                    postOwner, // 알림을 받을 사용자 (게시글 작성자)
                    currentMember.getNickname() + "님이 당신의 게시글에 대댓글을 남겼습니다.",
                    NotificationType.REPLY,
                    new RelateInfoDto(
                            comment.getSubTimeline().getId(), // postId
                            postOwner.getId(), // 게시글 작성자의 memberId
                            comment.getSubTimeline().getMainTimeline().getId() // mainTimelineId
                    )
            );
        }

        return new ReplyResponseDTO(
                savedReply.getId(),
                savedReply.getContent(),
                savedReply.getCreatedDate().toString(),
                savedReply.getMember().getNickname(),
                comment.getSubTimeline().getId(),
                comment.getMember().getId(),
                comment.getSubTimeline().getMainTimeline().getId(),
                savedReply.getLikes().size()
        );
    }

    // 댓글 ID로 대댓글 조회
    public List<ReplyResponseDTO> getRepliesByCommentId(Long commentId) {
        List<Reply> replies = replyRepository.findByCommentId(commentId);

        return replies.stream()
                .map(reply -> {
                    int likeCount = reply.getLikes().size();
                    return new ReplyResponseDTO(
                            reply.getId(),
                            reply.getContent(),
                            reply.getCreatedDate().toString(),
                            reply.getMember().getNickname(),
                            reply.getComment().getSubTimeline().getId(),
                            reply.getMember().getId(),
                            reply.getComment().getSubTimeline().getMainTimeline().getId(),
                            likeCount
                    );
                })
                .collect(Collectors.toList());
    }

    // 대댓글 수정 (작성자만 가능)
    public ReplyUpdateResponseDTO updateReply(Long replyId, ReplyUpdateRequestDTO requestDTO) {
        Reply reply = replyRepository.findById(replyId)
                .orElseThrow(() -> new ApiException(ErrorType._REPLY_NOT_FOUND, "해당 대댓글을 찾을 수 없습니다."));

        checkOwnership(reply.getMember().getEmail());

        reply.setContent(requestDTO.getContent());
        replyRepository.save(reply);
        return ReplyUpdateResponseDTO.success();
    }

    @Transactional
    public ReplyDeleteResponseDTO deleteReply(Long replyId) {
        Reply reply = replyRepository.findById(replyId)
                .orElseThrow(() -> new ApiException(ErrorType._REPLY_NOT_FOUND, "해당 대댓글을 찾을 수 없습니다."));

        checkOwnership(reply.getMember().getEmail());

        replyRepository.delete(reply);
        return ReplyDeleteResponseDTO.success();
    }

    private Member getCurrentAuthenticatedMember() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userEmail = authentication.getName();

        return memberRepository.findByEmailAndIsDeletedFalse(userEmail);
    }

    private void checkOwnership(String ownerEmail) {
        String userEmail = getCurrentAuthenticatedMember().getEmail();
        if (!userEmail.equals(ownerEmail)) {
            throw new ApiException(ErrorType._DO_NOT_HAVE_PERMISSION);
        }
    }
}
