package com.api.RecordTimeline.domain.member.repository;

import com.api.RecordTimeline.domain.member.domain.Interest;
import com.api.RecordTimeline.domain.member.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Member findByEmailAndIsDeletedFalse(String email);
    boolean existsByEmailAndIsDeletedFalse(String email);
    boolean existsByNicknameAndIsDeletedFalse(String nickname);

    @Query("SELECT m FROM Member m WHERE m.interest = ?1 AND m.email != ?2 AND m.isDeleted = false")
    List<Member> findMembersWithSameInterest(Interest interest, String email);


}
