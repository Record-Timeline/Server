package com.api.RecordTimeline.domain.notification.service;

import com.api.RecordTimeline.domain.member.domain.Member;
import com.api.RecordTimeline.domain.notification.domain.Notification;
import com.api.RecordTimeline.domain.notification.domain.NotificationType;
import com.api.RecordTimeline.domain.notification.dto.NotificationResponseDto;
import com.api.RecordTimeline.domain.notification.repository.NotificationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;


@Service
@RequiredArgsConstructor
public class NotificationService {

    private final NotificationRepository notificationRepository;
    private final SimpMessagingTemplate messagingTemplate;

    /**
     * 알림을 DB에 저장하고 WebSocket을 통해 실시간으로 전송하는 메서드
     *
     * @param sender  알림을 보낸 사람 (팔로우, 좋아요를 한 사람)
     * @param receiver 알림을 받을 사람
     * @param message  알림 메시지 내용
     * @param type     알림 타입 (팔로우, 좋아요 등)
     */
    @Transactional
    public void sendNotification(Member sender, Member receiver, String message, NotificationType type) {
        // 알림 만료일 설정 (테스트용으로 1분으로 설정해둠)
        LocalDateTime expiryDate = LocalDateTime.now().plusMinutes(1);

        // 알림 DB에 저장
        Notification notification = Notification.builder()
                .sender(sender)
                .receiver(receiver)
                .message(message)
                .isRead(false)  // 읽지 않은 상태로 설정
                .type(type)
                .createdAt(LocalDateTime.now())
                .expiryDate(expiryDate)
                .build();

        notificationRepository.save(notification);  // 알림 DB에 저장

        // 실시간 알림 전송 (WebSocket)
        sendRealTimeNotification(message, receiver.getId());
    }

    /**
     * WebSocket을 통해 실시간 알림을 전송하는 메서드
     *
     * @param message    전송할 메시지
     * @param receiverId 알림을 받을 사람의 ID
     */
    private void sendRealTimeNotification(String message, Long receiverId) {
        String destination = "/topic/notifications/" + receiverId;  // 전송할 경로 설정
        messagingTemplate.convertAndSend(destination, message);  // WebSocket을 통해 실시간 전송
        System.out.println("Notification sent to: " + destination + " with message: " + message);  // 전송 로그 출력
    }


    public List<NotificationResponseDto> getNotificationsForUser(Long userId) {

        return notificationRepository.findByReceiverId(userId).stream()
                .map(notification -> NotificationResponseDto.builder()
                        .id(notification.getId())
                        .message(notification.getMessage())
                        .createdAt(notification.getCreatedAt())
                        .isRead(notification.isRead())
                        .type(notification.getType().name())
                        .build())
                .toList();
    }
}
