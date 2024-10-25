package com.api.RecordTimeline.domain.notification.controller;

import com.api.RecordTimeline.domain.notification.domain.Notification;
import com.api.RecordTimeline.domain.notification.dto.NotificationResponseDto;
import com.api.RecordTimeline.domain.notification.repository.NotificationRepository;
import com.api.RecordTimeline.domain.member.domain.Member;
import com.api.RecordTimeline.domain.member.repository.MemberRepository;
import com.api.RecordTimeline.domain.notification.service.NotificationService;
import com.api.RecordTimeline.global.security.jwt.JwtAuthenticationToken;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/v1/notifications")
@RequiredArgsConstructor
public class NotificationController {

    private final NotificationRepository notificationRepository;
    private final MemberRepository memberRepository;
    private final NotificationService notificationService;


    @GetMapping
    public List<Notification> getNotifications(Long memberId) {
        return notificationRepository.findByReceiverId(memberId);
    }

    // 알림 조회 API
    @GetMapping("/my")

    // 해당 사용자에 맞는 알림 리스트 조회
    public ResponseEntity<List<NotificationResponseDto>> getUserNotifications(Authentication authentication) {
        Long userId = ((JwtAuthenticationToken) authentication).getUserId();
        List<NotificationResponseDto> notifications = notificationService.getNotificationsForUser(userId);
        return ResponseEntity.ok(notifications);
    }
}
