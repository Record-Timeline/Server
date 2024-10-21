package com.api.RecordTimeline.domain.notification.service;


import com.api.RecordTimeline.domain.notification.repository.NotificationRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class NotificationCleanupService {

    private final NotificationRepository notificationRepository;

    @Transactional
    //@Scheduled(cron = "0 0 0 * * ?")  // 매일 자정에 실행 (배포용)
    @Scheduled(cron = "0 * * * * ?")  // 1분마다 실행 (테스트용)
    public void deleteExpiredNotifications() {

        LocalDateTime now = LocalDateTime.now();
        notificationRepository.deleteByExpiryDateBefore(now);  // 만료일이 지난 알림 삭제
    }
}