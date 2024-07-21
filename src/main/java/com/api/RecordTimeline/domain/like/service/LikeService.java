package com.api.RecordTimeline.domain.like.service;

import com.api.RecordTimeline.domain.like.domain.UserLike;
import com.api.RecordTimeline.domain.like.dto.request.LikeRequestDTO;
import com.api.RecordTimeline.domain.like.dto.response.LikeResponseDTO;
import com.api.RecordTimeline.domain.like.repository.LikeRepository;
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

@Service
@RequiredArgsConstructor
public class LikeService {

    private final LikeRepository likeRepository;
    private final MemberRepository memberRepository;
    private final SubTimelineRepository subTimelineRepository;

    public LikeResponseDTO toggleLike(LikeRequestDTO likeRequestDTO) {
        Long subTimelineId = likeRequestDTO.getSubTimelineId();
        Member member = getCurrentAuthenticatedMember();
        SubTimeline subTimeline = subTimelineRepository.findById(subTimelineId)
                .orElseThrow(() -> new NoSuchElementException("SubTimeline not found"));

        Optional<UserLike> existingLike = likeRepository.findByMemberAndSubTimeline(member, subTimeline);

        if (existingLike.isPresent()) {
            likeRepository.delete(existingLike.get());
            subTimeline.adjustLikeCount(-1); // 좋아요 수 감소 및 검증
            subTimelineRepository.save(subTimeline); // 변경된 좋아요 수 저장
            return LikeResponseDTO.success("좋아요가 제거되었습니다.", subTimeline.getLikeCount());
        } else {
            UserLike like = UserLike.builder()
                    .member(member)
                    .subTimeline(subTimeline)
                    .build();
            likeRepository.save(like);
            subTimeline.adjustLikeCount(1); // 좋아요 수 증가 및 검증
            subTimelineRepository.save(subTimeline); // 변경된 좋아요 수 저장
            return LikeResponseDTO.success("좋아요가 추가되었습니다.", subTimeline.getLikeCount());
        }
    }

    private Member getCurrentAuthenticatedMember() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userEmail = authentication.getName();
        return Optional.ofNullable(memberRepository.findByEmailAndIsDeletedFalse(userEmail))
                .orElseThrow(() -> new NoSuchElementException("활성 상태의 해당 이메일로 등록된 사용자를 찾을 수 없습니다: " + userEmail));
    }
}
