package com.api.RecordTimeline.domain.career.service;

import com.api.RecordTimeline.domain.career.domain.Career;
import com.api.RecordTimeline.domain.career.domain.CareerDetail;
import com.api.RecordTimeline.domain.career.domain.Education;
import com.api.RecordTimeline.domain.career.repository.*;
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
    private final CareerRepository careerRepository;
    private final CertificateRepository certificateRepository;
    private final EducationRepository educationRepository;
    private final ForeignLanguageRepository foreignLanguageRepository;

    @Transactional(readOnly = true)
    public CareerDetail getCareerDetailByMemberId(Long memberId) {
        Member member = memberRepository.findByIdAndIsDeletedFalse(memberId);

        if (member == null) {
            throw new ApiException(ErrorType._USER_NOT_FOUND_DB);
        }

        CareerDetail careerDetail = careerDetailRepository.findByMember(member)
                .orElseThrow(() -> new ApiException(ErrorType._CAREER_DETAIL_NOT_FOUND));

        // 정렬된 데이터를 빌더를 통해 새로운 CareerDetail로 생성
        return CareerDetail.builder()
                .id(careerDetail.getId())
                .member(careerDetail.getMember())
                .careers(careerRepository.findAllByMemberIdOrderByStartDateAsc(memberId))
                .certificates(certificateRepository.findAllByMemberIdOrderByDateAsc(memberId))
                .educations(educationRepository.findAllByMemberIdOrderByStartDateAsc(memberId))
                .languages(foreignLanguageRepository.findAll())
                .build();
    }

    @Transactional
    public CareerDetail saveCareerDetail(CareerDetail careerDetail) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        Member member = memberRepository.findByEmailAndIsDeletedFalse(email);

        if (member == null) {
            throw new ApiException(ErrorType._USER_NOT_FOUND_DB);
        }

        // 빌더를 사용하여 CareerDetail에 Member를 설정하고 저장
        CareerDetail newCareerDetail = CareerDetail.builder()
                .member(member)
                .careers(careerDetail.getCareers())
                .certificates(careerDetail.getCertificates())
                .educations(careerDetail.getEducations())
                .languages(careerDetail.getLanguages())
                .build();

        return careerDetailRepository.save(newCareerDetail);
    }

    @Transactional
    public void deleteCareerDetail(Long careerDetailId) {
        CareerDetail careerDetail = getCareerDetailById(careerDetailId);
        careerDetailRepository.delete(careerDetail);
    }

    private CareerDetail getCareerDetailById(Long careerDetailId) {
        return careerDetailRepository.findById(careerDetailId)
                .orElseThrow(() -> new IllegalArgumentException("경력사항을 찾을 수 없습니다."));
    }
}
