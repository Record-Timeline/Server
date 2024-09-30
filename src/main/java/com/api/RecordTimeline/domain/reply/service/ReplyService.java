package com.api.RecordTimeline.domain.reply.service;

import com.api.RecordTimeline.domain.comment.domain.Comment;
import com.api.RecordTimeline.domain.comment.repository.CommentRepository;
import com.api.RecordTimeline.domain.member.domain.Member;
import com.api.RecordTimeline.domain.member.repository.MemberRepository;
import com.api.RecordTimeline.domain.reply.domain.Reply;
import com.api.RecordTimeline.domain.reply.dto.request.ReplyCreateRequestDTO;
import com.api.RecordTimeline.domain.reply.dto.request.ReplyUpdateRequestDTO;
import com.api.RecordTimeline.domain.reply.dto.response.ReplyDeleteResponseDTO;
import com.api.RecordTimeline.domain.reply.dto.response.ReplyResponseDTO;
import com.api.RecordTimeline.domain.reply.dto.response.ReplyUpdateResponseDTO;
import com.api.RecordTimeline.domain.reply.repository.ReplyRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReplyService {

    private final ReplyRepository replyRepository;
    private final CommentRepository commentRepository;
    private final MemberRepository memberRepository;

    public ReplyResponseDTO createReply(ReplyCreateRequestDTO request) {
        Comment comment = commentRepository.findById(request.getCommentId())
                .orElseThrow(() -> new IllegalArgumentException("해당 댓글을 찾을 수 없습니다."));

        Member member = memberRepository.findById(request.getMemberId())
                .orElseThrow(() -> new IllegalArgumentException("해당 사용자를 찾을 수 없습니다."));

        Reply reply = Reply.builder()
                .comment(comment) // 댓글과의 연관관계 설정
                .member(member)
                .content(request.getContent())
                .build();

        Reply savedReply = replyRepository.save(reply);

        return new ReplyResponseDTO(savedReply.getId(), savedReply.getContent(),
                savedReply.getCreatedDate().toString(), savedReply.getMember().getNickname());
    }

    public List<ReplyResponseDTO> getRepliesByCommentId(Long commentId) {
        List<Reply> replies = replyRepository.findByCommentId(commentId);

        return replies.stream()
                .map(reply -> new ReplyResponseDTO(
                        reply.getId(),
                        reply.getContent(),
                        reply.getCreatedDate().toString(),
                        reply.getMember().getNickname()))
                .collect(Collectors.toList());
    }

    public ReplyUpdateResponseDTO updateReply(Long replyId, ReplyUpdateRequestDTO requestDTO) {
        Reply reply = replyRepository.findById(replyId)
                .orElseThrow(() -> new IllegalArgumentException("해당 대댓글을 찾을 수 없습니다."));

        reply.setContent(requestDTO.getContent());  // 대댓글 내용 업데이트
        replyRepository.save(reply);
        return ReplyUpdateResponseDTO.success();  // 수정 성공 시 성공 응답 반환
    }

    @Transactional
    public ReplyDeleteResponseDTO deleteReply(Long replyId) {
        Reply reply = replyRepository.findById(replyId)
                .orElseThrow(() -> new IllegalArgumentException("해당 대댓글을 찾을 수 없습니다."));

        replyRepository.delete(reply);
        return ReplyDeleteResponseDTO.success();  // 삭제 성공 시 성공 응답 반환
    }
}

