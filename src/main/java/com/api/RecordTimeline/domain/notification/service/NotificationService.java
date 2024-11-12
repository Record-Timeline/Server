package com.api.RecordTimeline.domain.notification.service;

import com.api.RecordTimeline.domain.member.domain.Member;
import com.api.RecordTimeline.domain.notification.domain.Notification;
import com.api.RecordTimeline.domain.notification.domain.NotificationType;
import com.api.RecordTimeline.domain.notification.dto.NotificationResponseDto;
import com.api.RecordTimeline.domain.notification.repository.NotificationRepository;
import com.api.RecordTimeline.global.exception.ApiException;
import com.api.RecordTimeline.global.exception.ErrorType;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityNotFoundException;
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
    private final ObjectMapper objectMapper = new ObjectMapper();

    /**
     * 알림을 DB에 저장하고 WebSocket을 통해 실시간으로 전송하는 메서드
     */
    @Transactional
    public void sendNotification(Member sender, Member receiver, String message, NotificationType type, Long relatedId) {

        LocalDateTime expiryDate = LocalDateTime.now().plusDays(14);

        Notification notification = Notification.builder()
                .sender(sender)
                .receiver(receiver)
                .message(message)
                .isRead(false)
                .type(type)
                .createdAt(LocalDateTime.now())
                .expiryDate(expiryDate)
                .build();

        notificationRepository.save(notification);

        // NotificationResponseDto 생성
        NotificationResponseDto notificationDto = NotificationResponseDto.builder()
                .id(notification.getId())
                .message(notification.getMessage())
                .createdAt(notification.getCreatedAt())
                .isRead(notification.isRead())
                .type(notification.getType().name())
                .profileImageUrl(sender.getProfile() != null ? sender.getProfile().getProfileImgUrl() : "")
                .postId(type == NotificationType.LIKE ? relatedId : null)  // 좋아요 알림인 경우 postId 설정
                .followerId(type == NotificationType.FOLLOW ? relatedId : null) // 팔로우 알림인 경우 followerId 설정
                .build();

        // WebSocket을 통해 실시간 알림 전송
        sendRealTimeNotification(notificationDto, receiver.getId());
    }


    /**
     * WebSocket을 통해 실시간 알림을 전송하는 메서드
     */
    private void sendRealTimeNotification(NotificationResponseDto notificationDto, Long receiverId) {
        String destination = "/topic/notifications/" + receiverId;
        try {
            // DTO를 JSON 문자열로 변환
            String message = objectMapper.writeValueAsString(notificationDto);
            // WebSocket을 통해 JSON 문자열 전송
            messagingTemplate.convertAndSend(destination, message);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<NotificationResponseDto> getNotificationsForUser(Long userId) {

        List<Notification> notifications = notificationRepository.findByReceiverId(userId);

        if (notifications.isEmpty()) {
            throw new ApiException(ErrorType._NOTIFICATION_NOT_FOUND);
        }

        return notifications.stream()
                .map(notification -> {
                    String profileImageUrl = "";
                    if (notification.getSender().getProfile() != null) {
                        profileImageUrl = notification.getSender().getProfile().getProfileImgUrl();
                    }

                    return NotificationResponseDto.builder()
                            .id(notification.getId())
                            .message(notification.getMessage())
                            .createdAt(notification.getCreatedAt())
                            .isRead(notification.isRead())
                            .type(notification.getType().name())
                            .profileImageUrl(profileImageUrl)
                            .postId(notification.getType() == NotificationType.LIKE ? notification.getPostId() : null)  // 좋아요 알림일 때만 postId 설정
                            .followerId(notification.getType() == NotificationType.FOLLOW ? notification.getSender().getId() : null) // 팔로우 알림일 때만 followerId 설정
                            .build();
                })
                .toList();
    }

    @Transactional
    public void markNotificationAsRead(Long notificationId, Long userId) {
        // 알림 조회
        Notification notification = notificationRepository
                .findByIdAndReceiverId(notificationId, userId)
                .orElseThrow(() -> new EntityNotFoundException("해당 사용자의 알림을 찾을 수 없습니다."));

        // 읽음 처리
        Notification updatedNotification = Notification.builder()
                .id(notification.getId())
                .receiver(notification.getReceiver())
                .sender(notification.getSender())
                .message(notification.getMessage())
                .isRead(true)
                .createdAt(notification.getCreatedAt())
                .expiryDate(notification.getExpiryDate())
                .type(notification.getType())
                .build();

        // 업데이트된 알림 저장
        notificationRepository.save(updatedNotification);
    }

    @Transactional(readOnly = true)
    public long countUnreadNotifications(Long userId) {
        // 안 읽은 알림 개수 조회
        return notificationRepository.countByReceiverIdAndIsReadFalse(userId);
    }

}
