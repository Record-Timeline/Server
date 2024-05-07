package com.api.RecordTimeline.domain.mainPage.dto.response;

import com.api.RecordTimeline.domain.subTimeline.domain.SubTimeline;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class MainPageSubTimelineDto {
    private List<SubTimeline> subTimeline;

}
