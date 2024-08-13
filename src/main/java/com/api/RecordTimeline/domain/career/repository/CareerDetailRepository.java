package com.api.RecordTimeline.domain.career.repository;

import com.api.RecordTimeline.domain.career.domain.CareerDetail;
import com.api.RecordTimeline.domain.member.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CareerDetailRepository  extends JpaRepository<CareerDetail, Long> {
    Optional<CareerDetail> findByMember(Member member);
}
