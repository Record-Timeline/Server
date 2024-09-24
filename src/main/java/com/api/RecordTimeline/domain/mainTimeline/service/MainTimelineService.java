package com.api.RecordTimeline.domain.mainTimeline.service;

import com.api.RecordTimeline.domain.mainTimeline.domain.MainTimeline;
import com.api.RecordTimeline.domain.mainTimeline.dto.request.MainTimelineRequestDTO;
import com.api.RecordTimeline.domain.mainTimeline.dto.request.UpdateMainTimelineRequestDTO;
import com.api.RecordTimeline.domain.mainTimeline.dto.response.MainUpdateStatusResponseDTO;
import com.api.RecordTimeline.domain.mainTimeline.repository.MainTimelineRepository;
import com.api.RecordTimeline.domain.member.domain.Member;
import com.api.RecordTimeline.domain.member.repository.MemberRepository;
import com.api.RecordTimeline.global.exception.ApiException;
import com.api.RecordTimeline.global.exception.ErrorType;
import org.springframework.security.core.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

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
    public MainTimeline createMainTimeline(MainTimelineRequestDTO mainTimelineRequestDTO) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userEmail = authentication.getName();
        Member member = memberRepository.findByEmailAndIsDeletedFalse(userEmail);

        if (member == null) {
            throw new NoSuchElementException("활성 상태의 해당 이메일로 등록된 사용자를 찾을 수 없습니다: " + userEmail);
        }

        MainTimeline mainTimeline = mainTimelineRequestDTO.toEntity(member);
        return mainTimelineRepository.save(mainTimeline);
    }

    // ID를 이용해 메인 타임라인 조회
    public MainTimeline getMainTimelineById(Long id) {
        return mainTimelineRepository.findById(id).orElseThrow(() ->
                new NoSuchElementException("해당 ID로 메인 타임라인을 찾을 수 없습니다: " + id));
    }

    // 메인 타임라인 진행 상태 토글
    public MainUpdateStatusResponseDTO toggleMainTimelineDoneStatus(Long mainTimelineId) {
        MainTimeline mainTimeline = mainTimelineRepository.findById(mainTimelineId)
                .orElseThrow(() -> new ApiException(ErrorType._MAINTIMELINE_NOT_FOUND));

        checkOwnership(mainTimeline.getMember().getEmail());

        // 현재 상태를 반전시킴
        boolean newDoneStatus = !mainTimeline.isDone();
        mainTimeline.setDone(newDoneStatus);
        mainTimelineRepository.save(mainTimeline);

        String message = newDoneStatus ? "메인타임라인이 완료 상태로 업데이트 되었습니다." : "메인타임라인이 진행중 상태로 업데이트 되었습니다.";
        return MainUpdateStatusResponseDTO.success(newDoneStatus);
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
        mainTimeline.setPrivate(requestDTO.isPrivate());
        mainTimeline.setDone(requestDTO.isDone());

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
        return mainTimelineRepository.findByMemberIdOrderByStartDate(memberId).stream()
                .filter(mainTimeline -> !mainTimeline.isPrivate())  // 비공개 타임라인 제외
                .collect(Collectors.toList());
    }

    private void checkOwnership(String ownerEmail) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userEmail = authentication.getName();
        if (!userEmail.equals(ownerEmail)) {
            throw new ApiException(ErrorType._DO_NOT_HAVE_PERMISSION);
        }
    }

    public void setMainTimelinePrivacy(Long mainTimelineId, boolean isPrivate) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userEmail = authentication.getName();

        MainTimeline mainTimeline = mainTimelineRepository.findById(mainTimelineId)
                .orElseThrow(() -> new ApiException(ErrorType._MAINTIMELINE_NOT_FOUND));

        if (!mainTimeline.getMember().getEmail().equals(userEmail)) {
            throw new ApiException(ErrorType._DO_NOT_HAVE_PERMISSION);
        }

        mainTimeline.setPrivate(isPrivate);
        mainTimelineRepository.save(mainTimeline);
    }
}