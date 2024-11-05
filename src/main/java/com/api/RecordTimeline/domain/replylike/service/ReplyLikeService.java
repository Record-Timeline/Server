package com.api.RecordTimeline.domain.replylike.service;

import com.api.RecordTimeline.domain.like.dto.response.LikeResponseDTO;
import com.api.RecordTimeline.domain.member.domain.Member;
import com.api.RecordTimeline.domain.member.repository.MemberRepository;
import com.api.RecordTimeline.domain.reply.domain.Reply;
import com.api.RecordTimeline.domain.reply.repository.ReplyRepository;
import com.api.RecordTimeline.domain.replylike.domain.ReplyLike;
import com.api.RecordTimeline.domain.replylike.repository.ReplyLikeRepository;
import com.api.RecordTimeline.global.exception.ApiException;
import com.api.RecordTimeline.global.exception.ErrorType;
import com.api.RecordTimeline.global.security.jwt.JwtAuthenticationToken;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
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
            replyRepository.save(reply); // 좋아요 추가 시에만 저장
        }
        replyRepository.save(reply);
        return new LikeResponseDTO("SU", "Success", reply.getLikeCount());
    }

    private Member getCurrentAuthenticatedMember() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Long memberId = ((JwtAuthenticationToken) authentication).getUserId();
        return memberRepository.findById(memberId)
                .orElseThrow(() -> new ApiException(ErrorType._USER_NOT_FOUND_DB));
    }
}
