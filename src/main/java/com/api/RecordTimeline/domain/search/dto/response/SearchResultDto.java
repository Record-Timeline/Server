package com.api.RecordTimeline.domain.search.dto.response;

import com.api.RecordTimeline.domain.member.dto.response.MemberInfoResponseDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SearchResultDto {
    private List<MemberInfoResponseDto> members;
    private List<SearchSubTimelineDto> subTimelines;
}
