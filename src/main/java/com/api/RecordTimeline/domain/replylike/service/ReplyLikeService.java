package com.api.RecordTimeline.domain.replylike.service;

import com.api.RecordTimeline.domain.like.dto.response.LikeResponseDTO;
import com.api.RecordTimeline.domain.member.domain.Member;
import com.api.RecordTimeline.domain.member.repository.MemberRepository;
import com.api.RecordTimeline.domain.notification.domain.NotificationType;
import com.api.RecordTimeline.domain.notification.dto.RelateInfoDto;
import com.api.RecordTimeline.domain.notification.service.NotificationService;
import com.api.RecordTimeline.domain.reply.domain.Reply;
import com.api.RecordTimeline.domain.reply.repository.ReplyRepository;
import com.api.RecordTimeline.domain.replylike.domain.ReplyLike;
import com.api.RecordTimeline.domain.replylike.repository.ReplyLikeRepository;
import com.api.RecordTimeline.global.exception.ApiException;
import com.api.RecordTimeline.global.exception.ErrorType;
import com.api.RecordTimeline.global.security.jwt.JwtAuthenticationToken;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ReplyLikeService {

    private final ReplyLikeRepository replyLikeRepository;
    private final ReplyRepository replyRepository;
    private final MemberRepository memberRepository;
    private final NotificationService notificationService;

    @Transactional
    public LikeResponseDTO toggleReplyLike(Long replyId) {
        Member member = getCurrentAuthenticatedMember();
        Reply reply = replyRepository.findById(replyId)
                .orElseThrow(() -> new ApiException(ErrorType._REPLY_NOT_FOUND));

        Optional<ReplyLike> existingLike = replyLikeRepository.findByMemberAndReply(member, reply);

        if (existingLike.isPresent()) {
            replyLikeRepository.delete(existingLike.get());
            reply.adjustLikeCount(-1);
        } else {
            ReplyLike replyLike = new ReplyLike();
            replyLike.setMember(member);
            replyLike.setReply(reply);
            replyLikeRepository.save(replyLike);
            reply.adjustLikeCount(1);

            if (!reply.getMember().equals(member)) {
                notificationService.sendNotification(
                        member,
                        reply.getMember(),
                        member.getNickname() + "님이 당신의 대댓글을 좋아합니다.",
                        NotificationType.REPLY_LIKE,
                        new RelateInfoDto(
                                reply.getComment().getSubTimeline().getId(),
                                reply.getMember().getId(),
                                reply.getComment().getSubTimeline().getMainTimeline().getId()
                        )
                );
            }
        }

        replyRepository.save(reply);
        return new LikeResponseDTO("SU", "Success", reply.getLikeCount());
    }

    public boolean isReplyLikedByMember(Long replyId) {
        Member member = getCurrentAuthenticatedMember();
        Reply reply = replyRepository.findById(replyId)
                .orElseThrow(() -> new ApiException(ErrorType._REPLY_NOT_FOUND));
        return replyLikeRepository.findByMemberAndReply(member, reply).isPresent();
    }

    private Member getCurrentAuthenticatedMember() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || authentication instanceof AnonymousAuthenticationToken) {
            throw new ApiException(ErrorType._UNAUTHORIZED); // 인증되지 않은 사용자 예외 처리
        }
        Long memberId = ((JwtAuthenticationToken) authentication).getUserId();
        return memberRepository.findById(memberId)
                .orElseThrow(() -> new ApiException(ErrorType._USER_NOT_FOUND_DB));
    }
}
