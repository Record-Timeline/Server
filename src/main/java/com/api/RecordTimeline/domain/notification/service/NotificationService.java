package com.api.RecordTimeline.domain.notification.service;

import com.api.RecordTimeline.domain.member.domain.Member;
import com.api.RecordTimeline.domain.notification.domain.Notification;
import com.api.RecordTimeline.domain.notification.domain.NotificationType;
import com.api.RecordTimeline.domain.notification.repository.NotificationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class NotificationService {

    private final NotificationRepository notificationRepository;

    @Transactional
    public void sendNotification(Member sender, Member receiver, String message, NotificationType type) {
        Notification notification = Notification.builder()
                .sender(sender)
                .receiver(receiver)
                .message(message)
                .isRead(false)
                .type(type)
                .createdAt(LocalDateTime.now())
                .build();

        notificationRepository.save(notification);
    }
}