package com.api.RecordTimeline.domain.notification.repository;

import com.api.RecordTimeline.domain.notification.domain.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NotificationRepository extends JpaRepository<Notification, Long> {
    List<Notification> findByReceiverId(Long receiverId);  // 특정 사용자의 알림 목록 조회
}