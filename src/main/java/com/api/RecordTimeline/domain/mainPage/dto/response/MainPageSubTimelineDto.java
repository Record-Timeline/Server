package com.api.RecordTimeline.domain.mainPage.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MainPageSubTimelineDto {
    private List<SubtimelineDto> subTimelines;
}
