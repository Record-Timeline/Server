package com.api.RecordTimeline.domain.career.service;

import com.api.RecordTimeline.domain.career.domain.CareerDetail;
import com.api.RecordTimeline.domain.career.domain.ForeignLanguage;
import com.api.RecordTimeline.domain.career.repository.CareerDetailRepository;
import com.api.RecordTimeline.domain.career.repository.ForeignLanguageRepository;
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
public class ForeignLanguageService {

    private final ForeignLanguageRepository foreignLanguageRepository;
    private final MemberRepository memberRepository;
    private final CareerDetailRepository careerDetailRepository;

    @Transactional
    public ForeignLanguage addLanguage(ForeignLanguage language) {
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

            language = language.toBuilder()
                    .userEmail(userEmail)
                    .careerDetail(careerDetail) // CareerDetail 연관 설정
                    .build();

            return foreignLanguageRepository.save(language);
        } else {
            throw new ApiException(ErrorType._USER_NOT_FOUND_DB);
        }
    }

    @Transactional
    public ForeignLanguage updateLanguage(Long languageId, ForeignLanguage language) {
        ForeignLanguage existingLanguage = foreignLanguageRepository.findById(languageId)
                .orElseThrow(() -> new NoSuchElementException("외국어를 찾을 수 없습니다: " + languageId));

        checkOwnership(existingLanguage.getUserEmail());

        ForeignLanguage updatedLanguage = existingLanguage.update(language);
        return foreignLanguageRepository.save(updatedLanguage);
    }

    @Transactional
    public void deleteLanguage(Long languageId) {
        ForeignLanguage existingLanguage = foreignLanguageRepository.findById(languageId)
                .orElseThrow(() -> new NoSuchElementException("외국어를 찾을 수 없습니다: " + languageId));

        checkOwnership(existingLanguage.getUserEmail());

        foreignLanguageRepository.delete(existingLanguage);
    }

    private void checkOwnership(String ownerEmail) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userEmail = authentication.getName();
        if (!userEmail.equals(ownerEmail)) {
            throw new ApiException(ErrorType._DO_NOT_HAVE_PERMISSION);
        }
    }

    public ForeignLanguage getLanguageById(Long languageId) {
        return foreignLanguageRepository.findById(languageId)
                .orElseThrow(() -> new NoSuchElementException("외국어를 찾을 수 없습니다: " + languageId));
    }
}
