package com.api.RecordTimeline.domain.member.repository;

import com.api.RecordTimeline.domain.member.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Member findByEmailAndIsDeletedFalse(String email);
    boolean existsByEmailAndIsDeletedFalse(String email);
    boolean existsByNicknameAndIsDeletedFalse(String nickname);
}
