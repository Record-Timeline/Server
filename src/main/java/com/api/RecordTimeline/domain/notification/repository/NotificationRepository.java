package com.api.RecordTimeline.domain.notification.repository;

import com.api.RecordTimeline.domain.notification.domain.Notification;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface NotificationRepository extends JpaRepository<Notification, Long> {
    List<Notification> findByReceiverId(Long receiverId);  // 특정 사용자의 알림 목록 조회

    @Transactional
    void deleteByExpiryDateBefore(LocalDateTime expiryDate);

    @Query("SELECT n FROM Notification n WHERE n.id = :notificationId AND n.receiver.id = :userId")
    Optional<Notification> findByIdAndReceiverId(@Param("notificationId") Long notificationId, @Param("userId") Long userId);

    @Query("SELECT COUNT(n) FROM Notification n WHERE n.receiver.id = :userId AND n.isRead = false")
    long countByReceiverIdAndIsReadFalse(@Param("userId") Long userId);

}