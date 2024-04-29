package com.api.RecordTimeline.domain.profile.repository;

import com.api.RecordTimeline.domain.member.domain.Member;
import com.api.RecordTimeline.domain.profile.domain.Profile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProfileRepository extends JpaRepository<Profile, Long> {
    Profile findByMemberAndIsDeletedFalse(Member member);
}
