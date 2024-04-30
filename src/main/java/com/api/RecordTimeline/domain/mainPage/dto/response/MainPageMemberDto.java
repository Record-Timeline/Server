package com.api.RecordTimeline.domain.mainPage.dto.response;

import com.api.RecordTimeline.domain.mainTimeline.domain.MainTimeline;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class MainPageMemberDto {
    private String nickname;
    private String profileImageUrl;
    private String introduction;
    private List<MainTimeline> mainTimeline;

}
