package com.api.RecordTimeline.domain.notification.domain;

import com.api.RecordTimeline.domain.member.domain.Member;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "receiver_id", nullable = false)
    private Member receiver;  // 알림 받는 사람

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sender_id")
    private Member sender;    // 알림 보내는 사람

    private String message;   // 알림 내용

    private boolean isRead;   // 알림 읽음 여부

    private LocalDateTime createdAt; // 알림 생성 시간

    @Enumerated(EnumType.STRING)
    private NotificationType type;   // 알림 타입

    private LocalDateTime expiryDate; // 알림 만료일

    private Long postId;
    private Long followerId;

}
