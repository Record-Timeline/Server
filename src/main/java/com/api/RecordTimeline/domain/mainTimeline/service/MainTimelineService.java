package com.api.RecordTimeline.domain.mainTimeline.service;

import com.api.RecordTimeline.domain.mainTimeline.domain.MainTimeline;
import com.api.RecordTimeline.domain.mainTimeline.dto.request.UpdateMainTimelineRequestDTO;
import com.api.RecordTimeline.domain.mainTimeline.repository.MainTimelineRepository;
import com.api.RecordTimeline.domain.member.domain.Member;
import com.api.RecordTimeline.domain.member.repository.MemberRepository;
import com.api.RecordTimeline.global.exception.ApiException;
import com.api.RecordTimeline.global.exception.ErrorType;
import org.springframework.security.core.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.nio.file.AccessDeniedException;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class MainTimelineService {

    private final MainTimelineRepository mainTimelineRepository;
    private final MemberRepository memberRepository;

    @Autowired
    public MainTimelineService(MainTimelineRepository mainTimelineRepository, MemberRepository memberRepository) {
        this.mainTimelineRepository = mainTimelineRepository;
        this.memberRepository = memberRepository;
    }

    // 메인 타임라인 생성
    public MainTimeline createMainTimeline(MainTimeline mainTimeline) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userEmail = authentication.getName();
        Member member = memberRepository.findByEmailAndIsDeletedFalse(userEmail); // 이메일을 통해 활성 상태의 Member 조회

        if (member != null) {
            mainTimeline.setMember(member);
            return mainTimelineRepository.save(mainTimeline);
        } else {
            throw new NoSuchElementException("활성 상태의 해당 이메일로 등록된 사용자를 찾을 수 없습니다: " + userEmail);
        }
    }

    // ID를 이용해 메인 타임라인 조회
    public MainTimeline getMainTimelineById(Long id) {
        return mainTimelineRepository.findById(id).orElseThrow(() ->
                new NoSuchElementException("해당 ID로 메인 타임라인을 찾을 수 없습니다: " + id));
    }

    // 모든 메인 타임라인 조회
    public List<MainTimeline> getAllMainTimelines() {
        return mainTimelineRepository.findAll();
    }

    // 기존 메인 타임라인 업데이트
    public MainTimeline updateMainTimeline(Long id, UpdateMainTimelineRequestDTO requestDTO) {
        MainTimeline mainTimeline = mainTimelineRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("해당 ID로 메인 타임라인을 찾을 수 없습니다: " + id));

        checkOwnership(mainTimeline.getMember().getEmail());

        mainTimeline.setTitle(requestDTO.getTitle());
        mainTimeline.setStartDate(requestDTO.getStartDate());
        mainTimeline.setEndDate(requestDTO.getEndDate());

        return mainTimelineRepository.save(mainTimeline);
    }

    // 메인 타임라인 삭제
    public void deleteMainTimeline(Long id) {
        MainTimeline mainTimeline = mainTimelineRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("해당 ID로 메인 타임라인을 찾을 수 없습니다: " + id));

        checkOwnership(mainTimeline.getMember().getEmail());

        mainTimelineRepository.delete(mainTimeline);
    }

    // 현재 로그인된 사용자의 메인 타임라인 조회
    public List<MainTimeline> getMyTimelines() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userEmail = authentication.getName();
        Member member = memberRepository.findByEmailAndIsDeletedFalse(userEmail);

        if (member == null) {
            throw new NoSuchElementException("활성 상태의 해당 이메일로 등록된 사용자를 찾을 수 없습니다: " + userEmail);
        }

        return mainTimelineRepository.findByMemberIdOrderByStartDate(member.getId());
    }

    // 메인 타임라인 조회 - 정렬 로직 포함
    public List<MainTimeline> getTimelinesByMemberId(Long memberId) {
        return mainTimelineRepository.findByMemberIdOrderByStartDate(memberId);
    }

    private void checkOwnership(String ownerEmail) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userEmail = authentication.getName();
        if (!userEmail.equals(ownerEmail)) {
            throw new ApiException(ErrorType._DO_NOT_HAVE_PERMISSION);
        }
    }
}