package com.api.RecordTimeline.domain.subTimeline.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateSubTimelineRequestDTO {
    private String title;
    private String content;
    private LocalDate startDate;
    private LocalDate endDate;
    private Boolean isPrivate;  // Boolean 타입으로 수정하여 null 체크 가능
    private Boolean isDone;     // Boolean 타입으로 수정하여 null 체크 가능

    public void validate() {
        if (title == null || title.trim().isEmpty()) {
            throw new IllegalArgumentException("Title cannot be null or empty");
        }
        if (startDate == null) {
            throw new IllegalArgumentException("Start date cannot be null");
        }
        if (endDate != null && endDate.isBefore(startDate)) {
            throw new IllegalArgumentException("End date cannot be before start date");
        }
    }

    public boolean getIsPrivate() {
        return isPrivate;
    }

    public boolean getIsDone() {
        return isDone;
    }
}
