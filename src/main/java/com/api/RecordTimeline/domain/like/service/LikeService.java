package com.api.RecordTimeline.domain.like.service;

import com.api.RecordTimeline.domain.like.domain.UserLike;
import com.api.RecordTimeline.domain.like.dto.request.LikeRequestDTO;
import com.api.RecordTimeline.domain.like.dto.response.LikeResponseDTO;
import com.api.RecordTimeline.domain.like.repository.LikeRepository;
import com.api.RecordTimeline.domain.member.domain.Member;
import com.api.RecordTimeline.domain.member.repository.MemberRepository;
import com.api.RecordTimeline.domain.subTimeline.domain.SubTimeline;
import com.api.RecordTimeline.domain.subTimeline.repository.SubTimelineRepository;
import com.api.RecordTimeline.global.exception.ApiException;
import com.api.RecordTimeline.global.exception.ErrorType;
import com.api.RecordTimeline.global.security.jwt.JwtAuthenticationToken;
import jakarta.transaction.Transactional;
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

    @Transactional
    public LikeResponseDTO toggleLike(LikeRequestDTO likeRequestDTO) {
        Long subTimelineId = likeRequestDTO.getSubTimelineId();
        Member member = getCurrentAuthenticatedMember();

        // 서브타임라인 존재 여부 확인 및 예외 처리
        SubTimeline subTimeline = subTimelineRepository.findById(subTimelineId)
                .orElseThrow(() -> new ApiException(ErrorType._SUBTIMELINE_NOT_FOUND));

        Optional<UserLike> existingLike = likeRepository.findByMemberAndSubTimeline(member, subTimeline);

        if (existingLike.isPresent()) {
            likeRepository.delete(existingLike.get());
            subTimeline.adjustLikeCount(-1); // 좋아요 수 감소 및 검증
//            subTimelineRepository.save(subTimeline); // 변경된 좋아요 수 저장
            return LikeResponseDTO.success("removed successfully", subTimeline.getLikeCount());
        } else {
            UserLike like = UserLike.builder()
                    .member(member)
                    .subTimeline(subTimeline)
                    .build();
            likeRepository.save(like);
            subTimeline.adjustLikeCount(1); // 좋아요 수 증가 및 검증
            subTimelineRepository.save(subTimeline); // 변경된 좋아요 수 저장
            return LikeResponseDTO.success("added successfully", subTimeline.getLikeCount());
        }
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
