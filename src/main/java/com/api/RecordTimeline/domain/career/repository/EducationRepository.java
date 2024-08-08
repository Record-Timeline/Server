package com.api.RecordTimeline.domain.career.repository;

import com.api.RecordTimeline.domain.career.domain.Education;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EducationRepository extends JpaRepository<Education, Long> {
}