package com.api.RecordTimeline.domain.notification.controller;

import com.api.RecordTimeline.domain.notification.domain.Notification;
import com.api.RecordTimeline.domain.notification.repository.NotificationRepository;
import com.api.RecordTimeline.domain.member.domain.Member;
import com.api.RecordTimeline.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/notifications")
@RequiredArgsConstructor
public class NotificationController {

    private final NotificationRepository notificationRepository;
    private final MemberRepository memberRepository;

    @GetMapping
    public List<Notification> getNotifications(Long memberId) {
        return notificationRepository.findByReceiverId(memberId);
    }
}
