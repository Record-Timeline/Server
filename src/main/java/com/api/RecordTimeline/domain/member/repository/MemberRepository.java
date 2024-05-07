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

    @Query(value = "SELECT * FROM member WHERE interest = ?1 AND email != ?2 AND is_deleted = false ORDER BY RAND() LIMIT 5", nativeQuery = true)
    List<Member> findMembersWithSameInterest(String interest, String email);

    @Query(value = "SELECT * FROM member WHERE interest = ?1 AND is_deleted = false ORDER BY RAND() LIMIT 4", nativeQuery = true)
    List<Member> findRandomMembersByInterest(String interest);
}
