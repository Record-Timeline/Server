package com.api.RecordTimeline.domain.career.service;

import com.api.RecordTimeline.domain.career.domain.Career;
import com.api.RecordTimeline.domain.career.domain.CareerDetail;
import com.api.RecordTimeline.domain.career.domain.Education;
import com.api.RecordTimeline.domain.career.repository.CareerDetailRepository;
import com.api.RecordTimeline.domain.career.repository.CareerRepository;
import com.api.RecordTimeline.domain.career.repository.EducationRepository;
import com.api.RecordTimeline.domain.member.domain.Member;
import com.api.RecordTimeline.domain.member.repository.MemberRepository;
import com.api.RecordTimeline.global.exception.ApiException;
import com.api.RecordTimeline.global.exception.ErrorType;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CareerDetailService {

    private final CareerDetailRepository careerDetailRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public CareerDetail saveCareerDetail(CareerDetail careerDetail) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        Member member = memberRepository.findByEmailAndIsDeletedFalse(email);

        if (member == null) {
            throw new ApiException(ErrorType._USER_NOT_FOUND_DB);
        }

        careerDetail = careerDetail.toBuilder().member(member).build();
        return careerDetailRepository.save(careerDetail);
    }

    @Transactional
    public void deleteCareerDetail(Long careerDetailId) {
        CareerDetail careerDetail = getCareerDetailById(careerDetailId);
        careerDetailRepository.delete(careerDetail);
    }

    public CareerDetail getCareerDetailByMemberId(Long memberId) {
        Member member = memberRepository.findByIdAndIsDeletedFalse(memberId);

        if (member == null) {
            throw new ApiException(ErrorType._USER_NOT_FOUND_DB);
        }

        return careerDetailRepository.findByMember(member)
                .orElseThrow(() -> new ApiException(ErrorType._CAREER_DETAIL_NOT_FOUND));
    }

    private CareerDetail getCareerDetailById(Long careerDetailId) {
        return careerDetailRepository.findById(careerDetailId)
                .orElseThrow(() -> new IllegalArgumentException("경력사항을 찾을 수 없습니다."));
    }
}
