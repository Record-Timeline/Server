package com.api.RecordTimeline.domain.mainTimeline.service;

import com.api.RecordTimeline.domain.mainTimeline.domain.MainTimeline;
import com.api.RecordTimeline.domain.mainTimeline.repository.MainTimelineRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class MainTimelineService {

    private final MainTimelineRepository mainTimelineRepository;

    @Autowired
    public MainTimelineService(MainTimelineRepository mainTimelineRepository) {
        this.mainTimelineRepository = mainTimelineRepository;
    }

    // 메인 타임라인 생성
    public MainTimeline createMainTimeline(MainTimeline mainTimeline) {
        return mainTimelineRepository.save(mainTimeline);
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
    public MainTimeline updateMainTimeline(Long id, MainTimeline updatedMainTimeline) {
        MainTimeline mainTimeline = mainTimelineRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("해당 ID로 메인 타임라인을 찾을 수 없습니다: " + id));

        return mainTimelineRepository.save(mainTimeline);
    }

    // 메인 타임라인 삭제
    public void deleteMainTimeline(Long id) {
        MainTimeline mainTimeline = mainTimelineRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("해당 ID로 메인 타임라인을 찾을 수 없습니다: " + id));
        mainTimelineRepository.delete(mainTimeline);
    }

    // 메인 타임라인 조회 - 정렬 로직 포함
    public List<MainTimeline> getTimelinesByMemberId(Long memberId) {
        return mainTimelineRepository.findByMemberIdOrderByStartDate(memberId);
    }
}
