package com.api.RecordTimeline.domain.career.service;

import com.api.RecordTimeline.domain.career.domain.Career;
import com.api.RecordTimeline.domain.career.domain.CareerDetail;
import com.api.RecordTimeline.domain.career.domain.Education;
import com.api.RecordTimeline.domain.career.repository.CareerDetailRepository;
import com.api.RecordTimeline.domain.career.repository.CareerRepository;
import com.api.RecordTimeline.domain.career.repository.EducationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CareerDetailService {

    private final CareerDetailRepository careerDetailRepository;
    private final CareerRepository careerRepository;
    private final EducationRepository educationRepository;

    @Transactional
    public CareerDetail saveCareerDetail(CareerDetail careerDetail) {
        return careerDetailRepository.save(careerDetail);
    }

    @Transactional
    public void deleteCareerDetail(Long careerDetailId) {
        careerDetailRepository.deleteById(careerDetailId);
    }

    @Transactional
    public Career addCareer(Long careerDetailId, Career career) {
        CareerDetail careerDetail = getCareerDetailById(careerDetailId);
        careerDetail.getCareers().add(career);
        careerRepository.save(career);
        return career;
    }

    @Transactional
    public Education addEducation(Long careerDetailId, Education education) {
        CareerDetail careerDetail = getCareerDetailById(careerDetailId);
        careerDetail.getEducations().add(education);
        educationRepository.save(education);
        return education;
    }

    @Transactional
    public Career updateCareer(Long careerId, Career career) {
        Career existingCareer = getCareerById(careerId);
        Career updatedCareer = existingCareer.update(career);
        return careerRepository.save(updatedCareer);
    }

    @Transactional
    public Education updateEducation(Long educationId, Education education) {
        Education existingEducation = getEducationById(educationId);
        Education updatedEducation = existingEducation.update(education);
        return educationRepository.save(updatedEducation);
    }

    private CareerDetail getCareerDetailById(Long careerDetailId) {
        return careerDetailRepository.findById(careerDetailId)
                .orElseThrow(() -> new IllegalArgumentException("경력사항을 찾을 수 없습니다."));
    }

    private Career getCareerById(Long careerId) {
        return careerRepository.findById(careerId)
                .orElseThrow(() -> new IllegalArgumentException("경력을 찾을 수 없습니다."));
    }

    private Education getEducationById(Long educationId) {
        return educationRepository.findById(educationId)
                .orElseThrow(() -> new IllegalArgumentException("학력을 찾을 수 없습니다."));
    }
}
