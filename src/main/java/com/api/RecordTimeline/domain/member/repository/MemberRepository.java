package com.api.RecordTimeline.domain.member.repository;

import com.api.RecordTimeline.domain.member.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Member findByEmail (String email);
    boolean existsByEmail(String email);
    boolean existsByNickname(String nickname);
}
