package com.api.RecordTimeline.domain.mainPage.dto.response;

import com.api.RecordTimeline.domain.mainTimeline.domain.MainTimeline;
import com.api.RecordTimeline.domain.mainTimeline.dto.request.MainTimelineRequestDTO;
import com.api.RecordTimeline.domain.mainTimeline.dto.response.ReadResponseDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class MainPageMemberDto {
    private String nickname;
    private String profileImageUrl;
    private String introduction;
    private List<MainTimelineDto> mainTimeline;
}
