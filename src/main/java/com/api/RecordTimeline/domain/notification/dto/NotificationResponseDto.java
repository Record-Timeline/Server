package com.api.RecordTimeline.domain.notification.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@AllArgsConstructor
@Builder
@Getter
public class NotificationResponseDto {

    private Long id;
    private String message;
    private LocalDateTime createdAt;
    private boolean isRead;
    private String type;
}
