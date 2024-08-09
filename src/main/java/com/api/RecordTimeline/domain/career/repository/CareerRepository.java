package com.api.RecordTimeline.domain.career.repository;

import com.api.RecordTimeline.domain.career.domain.Career;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CareerRepository extends JpaRepository<Career, Long> {
}