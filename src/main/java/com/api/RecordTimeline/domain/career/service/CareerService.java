package com.api.RecordTimeline.domain.career.service;

import com.api.RecordTimeline.domain.career.domain.Career;
import com.api.RecordTimeline.domain.career.domain.CareerDetail;
import com.api.RecordTimeline.domain.career.repository.CareerDetailRepository;
import com.api.RecordTimeline.domain.career.repository.CareerRepository;
import com.api.RecordTimeline.domain.member.domain.Member;
import com.api.RecordTimeline.domain.member.repository.MemberRepository;
import com.api.RecordTimeline.global.exception.ApiException;
import com.api.RecordTimeline.global.exception.ErrorType;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class CareerService {

    private final CareerRepository careerRepository;
    private final MemberRepository memberRepository;
    private final CareerDetailRepository careerDetailRepository;

    @Transactional
    public Career addCareer(Career career) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userEmail = authentication.getName();
        Member member = memberRepository.findByEmailAndIsDeletedFalse(userEmail);

        if (member != null) {
            // CareerDetail이 없는 경우 새로 생성
            CareerDetail careerDetail = careerDetailRepository.findByMember(member)
                    .orElseGet(() -> {
                        CareerDetail newCareerDetail = CareerDetail.builder()
                                .member(member)
                                .build();
                        return careerDetailRepository.save(newCareerDetail);
                    });

            career = career.toBuilder()
                    .userEmail(userEmail)
                    .careerDetail(careerDetail) // CareerDetail 연관 설정
                    .build();

            return careerRepository.save(career);
        } else {
            throw new ApiException(ErrorType._USER_NOT_FOUND_DB);
        }
    }

    @Transactional
    public Career updateCareer(Long careerId, Career career) {
        Career existingCareer = careerRepository.findById(careerId)
                .orElseThrow(() -> new NoSuchElementException("경력을 찾을 수 없습니다: " + careerId));

        checkOwnership(existingCareer.getUserEmail());

        Career updatedCareer = existingCareer.update(career);
        return careerRepository.save(updatedCareer);
    }

    @Transactional
    public void deleteCareer(Long careerId) {
        Career existingCareer = careerRepository.findById(careerId)
                .orElseThrow(() -> new NoSuchElementException("경력을 찾을 수 없습니다: " + careerId));

        checkOwnership(existingCareer.getUserEmail());

        careerRepository.delete(existingCareer);
    }

    @Transactional(readOnly = true)
    public List<Career> getCareersByMemberId(Long memberId) {
        return careerRepository.findAllByMemberIdOrderByStartDateAsc(memberId);
    }

    private void checkOwnership(String ownerEmail) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userEmail = authentication.getName();
        if (!userEmail.equals(ownerEmail)) {
            throw new ApiException(ErrorType._DO_NOT_HAVE_PERMISSION);
        }
    }
}
