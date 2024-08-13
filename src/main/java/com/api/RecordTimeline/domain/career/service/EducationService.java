package com.api.RecordTimeline.domain.career.service;

import com.api.RecordTimeline.domain.career.domain.CareerDetail;
import com.api.RecordTimeline.domain.career.domain.Education;
import com.api.RecordTimeline.domain.career.repository.CareerDetailRepository;
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

import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class EducationService {

    private final EducationRepository educationRepository;
    private final MemberRepository memberRepository;
    private final CareerDetailRepository careerDetailRepository;

    @Transactional
    public Education addEducation(Education education) {
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

            education = education.toBuilder()
                    .userEmail(userEmail)
                    .careerDetail(careerDetail) // CareerDetail 연관 설정
                    .build();

            return educationRepository.save(education);
        } else {
            throw new ApiException(ErrorType._USER_NOT_FOUND_DB);
        }
    }

    @Transactional
    public Education updateEducation(Long educationId, Education education) {
        Education existingEducation = educationRepository.findById(educationId)
                .orElseThrow(() -> new NoSuchElementException("학력을 찾을 수 없습니다: " + educationId));

        checkOwnership(existingEducation.getUserEmail());

        Education updatedEducation = existingEducation.update(education);
        return educationRepository.save(updatedEducation);
    }

    @Transactional
    public void deleteEducation(Long educationId) {
        Education existingEducation = educationRepository.findById(educationId)
                .orElseThrow(() -> new NoSuchElementException("학력을 찾을 수 없습니다: " + educationId));

        checkOwnership(existingEducation.getUserEmail());

        educationRepository.delete(existingEducation);
    }

    private void checkOwnership(String ownerEmail) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userEmail = authentication.getName();
        if (!userEmail.equals(ownerEmail)) {
            throw new ApiException(ErrorType._DO_NOT_HAVE_PERMISSION);
        }
    }
}
