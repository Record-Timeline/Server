package com.api.RecordTimeline.domain.subTimeline.repository;

import com.api.RecordTimeline.domain.subTimeline.domain.SubTimeline;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SubTimelineRepository extends JpaRepository<SubTimeline, Long> {
    List<SubTimeline> findByMainTimelineId(Long mainTimelineId);

    // 서브 타임라인을 시작 날짜 기준으로 정렬하여 조회하는 쿼리
    @Query("select s from SubTimeline s where s.mainTimeline.id = ?1 order by s.startDate")
    List<SubTimeline> findByMainTimelineIdOrderByStartDate(Long mainTimelineId);
    @Query("""

            SELECT st FROM SubTimeline st 
                JOIN st.mainTimeline mt 
                JOIN mt.member m 
            WHERE (st.title LIKE %:keyword% OR st.content LIKE %:keyword%) 
            AND m.isDeleted = false
            """)
    List<SubTimeline> findByTitleOrContentContaining(@Param("keyword") String keyword);

    @Query(value = """

            SELECT st.* FROM sub_timeline st JOIN main_timeline mt ON st.main_timeline_id = mt.id 
                JOIN member m ON mt.member_id = m.id 
            WHERE m.interest = :interest 
                AND m.email != :email AND m.is_deleted = false 
            ORDER BY RAND() LIMIT 4
            """   , nativeQuery = true)
    List<SubTimeline> findSubTimelinesByInterestExcludingEmail(String interest, String email);

    @Query(value = """

            SELECT st.* FROM sub_timeline st JOIN main_timeline mt ON st.main_timeline_id = mt.id 
                JOIN member m ON mt.member_id = m.id 
            WHERE m.interest = :interest 
                AND m.is_deleted = false 
            ORDER BY RAND() LIMIT 4
            """, nativeQuery = true)
    List<SubTimeline> findSubTimelinesByInterest(String interest);

}



