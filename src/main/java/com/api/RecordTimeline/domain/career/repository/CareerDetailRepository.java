package com.api.RecordTimeline.domain.career.repository;

import com.api.RecordTimeline.domain.career.domain.CareerDetail;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CareerDetailRepository  extends JpaRepository<CareerDetail, Long> {
}
